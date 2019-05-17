package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.*;
import diaballik.serialization.DiabalikJacksonProvider;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

public class TestGameResource {
	@SuppressWarnings("unused")
	@RegisterExtension
	JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	Player p1;
	Player p2;
	Board board;
	Game game;

	private Application configureJersey() {
		return new ResourceConfig().
				register(GameResource.class).
				register(MyExceptionMapper.class).
				register(JacksonFeature.class).
				register(DiabalikJacksonProvider.class).
				property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeEach
	void setUp() {
		p1 = new Player("Foo", new Color(1));
		p2 = new Player("Bar", new Color(0));
		board = new StandardBuilder().build(p1, p2);
		game = new NewGameBuilder(p1, p2, board).build();
	}

	@Test
	void testNewGamePvP(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		Response responseMsg = client
				.target(baseUri)
				.path("game/newGamePvP/Foo/Bar/1/0/standard")
				.request()
				.post(Entity.text(""));

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		Game gameWithID = responseMsg.readEntity(Game.class);
		assertEquals(game, gameWithID);
	}

	@Test
	void testSave(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		client.target(baseUri)
				.path("game/newGamePvP/Foo/Bar/1/0/standard")
				.request()
				.post(Entity.text(""));

		Response responseMsg = client
				.target(baseUri)
				.path("game/save/saveX")
				.request()
				.put(Entity.text(""));

		assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
		Game gameWithID = responseMsg.readEntity(Game.class);
		assertEquals(game, gameWithID);

		try {
			final String content = new String(Files.readAllBytes(Paths.get("saves/saveX.txt")),
					StandardCharsets.UTF_8);
			Game gameRead = new DiabalikJacksonProvider().getMapper().readValue(content, Game.class);
			assertEquals(game, gameRead);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}


	}


	@Test
	void testTemplate(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		final Response res = client.
			target(baseUri).
			path("foo/bar"). //TODO
			request().
			put(Entity.text(""));
	}
}
