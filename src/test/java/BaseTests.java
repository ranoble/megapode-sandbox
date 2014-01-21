import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.util.Timeout;

import com.gravspace.abstractions.IWidget;
import com.gravspace.proxy.Widgets;
import com.gravspace.sandbox.widget.CommentInputWidget;

import core.CallableContainer;
import core.GetCallable;
import core.TestCoordinator;

public class BaseTests{

	private static ActorSystem system;
	private static Properties config;
	private static CallableContainer cc;

	public BaseTests() throws IOException {
		super();
	}

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
		config = new Properties();
		try {
			config.load(Properties.class
					.getResourceAsStream("/megapode.conf"));
			cc = new CallableContainer();
			ActorRef master = system.actorOf(
					Props.create(TestCoordinator.class, config, cc),
					"Coordinator");
			Timeout timeout = new Timeout(Duration.create(1, "minute"));
			Future<Object> routerMessage = Patterns.ask(master,
					new GetCallable(), timeout);				
			cc = (CallableContainer) Await.result(routerMessage, Duration.create(1, "minute"));
			Assert.assertNotNull(cc.getCallable());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void teardown() {
		JavaTestKit.shutdownActorSystem(system);
		system = null;
	}

	
	@Test
	public void testWidget() throws Exception {
		new JavaTestKit(system) {
			{
				IWidget data = Widgets.get(CommentInputWidget.class, cc.getCallable());//getDefaultRender(cc.getCallable());

				Future<String> result = data.build(1);
				
				String res = (String) Await.result(result, Duration.create(1, "minute"));
				System.out.println(res);
				Assert.assertNotNull(res);
			}
		};
	}


}
