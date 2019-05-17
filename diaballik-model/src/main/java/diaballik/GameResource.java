package diaballik;

import diaballik.serialization.DiabalikJacksonProvider;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Singleton
@Path("game")
@Api(value = "game", description = "Operations on Diabalik")
public class GameResource {
    private Optional<Game> game;

    public GameResource() {
        super();
        game = Optional.empty();
    }

    @POST
    @Path("newGamePvP/{name1}/{name2}/{color1}/{color2}/{board_type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game newGamePvP(@PathParam("name1") final String name1,
                           @PathParam("name2") final String name2,
                           @PathParam("color1") final int color1,
                           @PathParam("color2") final int color2,
                           @PathParam("board_type") final String boardType) {
        final Player player1 = createPlayer(name1, color1);
        final Player player2 = createPlayer(name2, color2);
        final Board board = createBoard(player1, player2, boardType);

        final GameBuilder gameBuilder = new NewGameBuilder(player1, player2, board);
        final Game game = gameBuilder.build();
        game.updateBoard();

        this.game = Optional.of(game);

        return game;
    }

    @POST
    @Path("newGamePvE/{name1}/{name2}/{color1}/{color2}/{ai}/{board_type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game newGamePvE(@PathParam("name1") final String name1,
                           @PathParam("name2") final String name2,
                           @PathParam("color1") final int color1,
                           @PathParam("color2") final int color2,
                           @PathParam("ai") final String ai,
                           @PathParam("board_type") final String boardType) {
        final Player player1 = createPlayer(name1, color1);
        final BotPlayer player2 = createBot(name2, color2, ai);
        final Board board = createBoard(player1, player2, boardType);

        final GameBuilder gameBuilder = new NewGameBuilder(player1, player2, board);
        final Game game = gameBuilder.build();
        game.updateBoard();

        this.game = Optional.of(game);
        return game;
    }

    @POST
    @Path("load/{savename}/{replay}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game load(@PathParam("savename") final String saveName,
                     @PathParam("replay") final boolean replay) {
        try {
            final String content = new String(Files.readAllBytes(Paths.get("saves/" + saveName + ".txt")),
                    StandardCharsets.UTF_8);
            final Game game = new DiabalikJacksonProvider().getMapper().readValue(content, Game.class);
            if(replay) {
                game.updateBoard();
            }
            this.game = Optional.of(game);
            return game;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PUT
    @Path("save/{savename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game save(@PathParam("savename") final String saveName) {
        try {
            final File file = new File("saves/" + saveName + ".txt");
            new DiabalikJacksonProvider().getMapper().writeValue(file, game.get());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return game.get();
    }

    @PUT
    @Path("movePiece/{oldx}/{oldy}/{newx}/{newy}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board movePiece(@PathParam("oldx") final int oldX,
                           @PathParam("oldy") final int oldY,
                           @PathParam("newx") final int newX,
                           @PathParam("newy") final int newY) {
        if (!game.isPresent()) {
            return null;
        }
        final Position oldPos = new Position(oldX, oldY);
        final Position newPos = new Position(newX, newY);
        final MovePiece action = new MovePiece(oldPos, newPos);
        action.execute(game.get().getBoard());
        game.get().addDone(action);
        return game.get().getBoard();
    }

    @PUT
    @Path("throwBall/{oldx}/{oldy}/{newx}/{newy}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board throwBall(@PathParam("oldx") final int oldX,
                           @PathParam("oldy") final int oldY,
                           @PathParam("newx") final int newX,
                           @PathParam("newy") final int newY) {
        if (!game.isPresent()) {
            return null;
        }
        final Position oldPos = new Position(oldX, oldY);
        final Position newPos = new Position(newX, newY);
        final ThrowBall action = new ThrowBall(oldPos, newPos);
        action.execute(game.get().getBoard());
        game.get().addDone(action);
        return game.get().getBoard();
    }

    @GET
    @Path("getBoard")
    @Produces(MediaType.APPLICATION_JSON)
    public Board getBoard() {
        if (game.isPresent()) {
            return game.get().getBoard();
        } else {
            return null;
        }
    }

    @GET
    @Path("getActions/{x}/{y}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Action> getActions(@PathParam("x") final int x,
                                   @PathParam("y") final int y) {
        if (!game.isPresent()) {
            return null;
        }
        final Piece p = game.get().getBoard().getPiece(new Position(x, y));
        if (p == null) {
            return null;
        }
        return game.get().doableActions(p);
    }

    @GET
    @Path("getAIDecision")
    @Produces(MediaType.APPLICATION_JSON)
    public Action AIDecision() {
        if (!game.isPresent()) {
            return null;
        }
        final BotPlayer bot = (BotPlayer) game.get().getPlayers().stream()
                .filter(p -> p instanceof BotPlayer)
                .findFirst()
                .orElse(null);
        if (bot == null) {
            return null;
        }
        return bot.getAI().decide(game.get()); //.execute(game.get().getBoard());
    }

    private BotPlayer createBot(final String name, final int color, final String ai) {
        final Color c = new Color(color);
        final BotPlayer bot = new BotPlayer(name, c);
        if ("noob".equals(ai)) {
            bot.setAI(new NoobAI(bot));
        } else if ("starting".equals(ai)) {
            bot.setAI(new StartingAI(bot));
        } else {
            bot.setAI(new ProgressiveAI(bot));
        }
        return bot;
    }

    private Player createPlayer(final String name, final int color) {
        final Color c = new Color(color);
        return new Player(name, c);
    }

    private Board createBoard(final Player player1, final Player player2, final String boardType) {
        final BoardBuilder boardBuilder;
        if ("standard".equals(boardType)) {
            boardBuilder = new StandardBuilder();
        } else if ("ball_random".equals(boardType)) {
            boardBuilder = new BallRandomBuilder();
        } else {
            boardBuilder = new EnemyAmongUsBuilder();
        }
        return boardBuilder.build(player1, player2);
    }
}
