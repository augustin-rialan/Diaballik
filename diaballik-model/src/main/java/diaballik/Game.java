package diaballik;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Game {

	private int nbTurns;

	private boolean finished;

	private ArrayList<Player> players;

	@JsonIgnore
	private Board board;

	private Board initialBoard;

	private ArrayList<Action> done;

	public void play() {

	}

	public void turn(final Player player) {

	}

    /**
     * Constructeur de Game pour une nouvelle partie
     * @param players
     * @param initialBoard
     */
	public Game(final ArrayList<Player> players,
				final Board initialBoard) {
		this.nbTurns = 0;
		this.finished = false;
		this.players = new ArrayList<>(players);
		this.initialBoard = initialBoard;
		//this.board = new Board(initialBoard);
		this.done = new ArrayList<>();
	}
    /**
     * Constructeur de Game pour charger une partie
     * @param players
     * @param initialBoard
     */
	public Game(final int nbTurns,
				final boolean finished,
				final ArrayList<Player> players,
				final Board board,
				final Board initialBoard,
				final ArrayList<Action> done) {
		this.nbTurns = nbTurns;
		this.finished = finished;
		this.players = new ArrayList<>(players);
		this.board = board;
		this.initialBoard = initialBoard;
		this.done = new ArrayList<>(done);
	}

	@JsonCreator
	public Game(@JsonProperty("nbturns") final int nbTurns,
				@JsonProperty("finished") final boolean finished,
				@JsonProperty("players") final ArrayList<Player> players,
				@JsonProperty("initialboard") final Board initialBoard,
				@JsonProperty("done") final ArrayList<Action> done) {
	    this.nbTurns = nbTurns;
	    this.finished = finished;
		this.initialBoard = initialBoard;
		this.players = new ArrayList<>(players);
	    this.done = new ArrayList<>(done);
    }

    /**
     * Met à jour le plateau actuel à partir du plateau initial quand on charge ou crée une partie. Lis toute les pièces au joueur
     * et rejoue toute les actions.
     */
    public void updateBoard() {
		board = new Board(initialBoard);
		players.stream().forEach(p -> {
			board.pieceList().stream()
					.filter(pc -> pc.getPlayer().equals(p))
					.forEach(pc -> p.addPiece(pc));
		});
	    /*for(Action action : done) {
	    	action.execute(board);
		}*/
	}

    /**
     * Rend la liste de tout les déplacements possibles pour une pièce donnée
     * @param p Une pièce
     * @return List<MovePiece>
     */
	public List<MovePiece> possibleMoves(final Piece p) {
		if(p.hasBall()) {
			return new ArrayList<>();
		}

		final List<Position> adj = new ArrayList<>();
		final List<MovePiece> actions = new ArrayList<>();

		final int x = p.getX();
		final int y = p.getY();

		if(x != 0) {
			adj.add(new Position(x - 1, y));
		}
		if(x != 6) {
			adj.add(new Position(x + 1, y));
		}
		if(y != 0) {
			adj.add(new Position(x, y - 1));
		}
		if(y != 6) {
			adj.add(new Position(x, y + 1));
		}

		adj.forEach(pos -> {
			if(board.getPiece(pos) == null) {
				actions.add(new MovePiece(new Position(p.getX(), p.getY()), pos));
			}
		});

		return actions;
	}

    /**
     * Rend la liste de tout les déplacements de balle possible pour une pièce donnée
     * @param p
     * @return List<ThrowBall>
     */
	public List<ThrowBall> possibleThrows(final Piece p) {
		if(!p.hasBall()) {
			return new ArrayList<>();
		}

		final List<Position> targets = new ArrayList<>();
		targets.add(inspectLine(p, -1, -1));
		targets.add(inspectLine(p, -1, 0));
		targets.add(inspectLine(p, -1, 1));
		targets.add(inspectLine(p, 0, -1));
		targets.add(inspectLine(p, 0, 1));
		targets.add(inspectLine(p, 1, -1));
		targets.add(inspectLine(p, 1, 0));
		targets.add(inspectLine(p, 1, 1));

		final List<ThrowBall> actions = targets.stream()
				.filter(Objects::nonNull).map(pos ->
				new ThrowBall(new Position(p.getX(), p.getY()), pos))
				.collect(Collectors.toList());

		return actions;
	}

	private Position inspectLine(final Piece p, final int dx, final int dy) {
		final Position position =
				Stream.iterate(new Position(p.getX(), p.getY()), pos -> {
					pos.setX(pos.getX() + dx);
					pos.setY(pos.getY() + dy);
					return pos;
				}).limit(7)
				.filter(Position::isValid)
				.filter(pos -> {
					final Piece piece = board.getPiece(pos);
					if(piece != null) {
						return !piece.equals(p) && !piece.hasBall();
					} else {
						return false;
					}
				}).findFirst()
				.filter(pos -> {
					final Piece piece = board.getPiece(pos);
					return piece.getPlayer().equals(p.getPlayer());
				})
				.orElse(null);

		return position;
	}

    /**
     * Utilise les méthodes possiblesMoves et possibleThrows pour rendre la liste de tout les actions possibles
     * @param p
     * @return List<Action>
     */
	public List<Action> doableActions(final Piece p) {

		final List<Action> actions = new ArrayList<>();

		actions.addAll(possibleMoves(p));
		actions.addAll(possibleThrows(p));

		return actions;
	}

    public Board getBoard() {
	    return this.board;
    }

	@JsonIdentityReference
	@Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Player> getPlayers() {
		return new ArrayList<>(players);
	}

	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Action> getDone() {
		return new ArrayList<>(done);
	}

	public void addDone(final Action a) {
		done.add(a);
	}

	public boolean isFinished() {
		return finished;
	}

	public int getNbTurns() {
		return nbTurns;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Game game = (Game) o;
		return nbTurns == game.nbTurns &&
				finished == game.finished &&
				Objects.equals(players, game.players) &&
				Objects.equals(board, game.board) &&
				Objects.equals(done, game.done);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nbTurns, finished, players, board, done);
	}

	@Override
	public String toString() {
		return "Game{" +
				"\nnbTurns=" + nbTurns +
				",\n finished=" + finished +
				",\n players=" + players +
				",\n board=" + board +
				",\n initialBoard=" + initialBoard +
				",\n done=" + done +
				'}';
	}
}
