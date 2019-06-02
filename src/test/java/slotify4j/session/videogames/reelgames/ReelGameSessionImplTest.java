package slotify4j.session.videogames.reelgames;

import org.junit.jupiter.api.Test;
import slotify4j.session.GameSessionConfig;
import slotify4j.session.GameSessionImplTest;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ReelGameSessionImplTest {

    public static void testDefaultReelGameSessionHasProperInitialValues(ReelGameSession session, ReelGameSessionConfig config) {
        assertEquals(session.getWinningAmount(), 0);
        assertEquals(session.getPaytable(), config.getPaytable());
        assertEquals(session.getReelsItemsSequences().length, config.getReelsItemsSequences().length);
        assertEquals(session.getReelsItemsNumber(), config.getReelsItemsNumber());
        assertEquals(session.getReelsNumber(), config.getReelsNumber());
        assertNull(session.getReelsItems());
        assertNull(session.getWinningLines());
        assertNull(session.getWinningScatters());
    }

    public static void testPlayUntilWin(ReelGameSession session, ReelGameSessionConfig config) throws Exception {
        long lastBet = 0;
        long lastCredits = 0;
        boolean wasLinesWin = false;
        boolean wasScattersWin = false;

        int timesToPlay = 1000;
        for (int i = 0; i < timesToPlay; i++) {
            while (session.getWinningAmount() == 0 || wasLinesWin || wasScattersWin) {
                wasLinesWin = false;
                wasScattersWin = false;
                lastCredits = session.getCreditsAmount();
                lastBet = session.getBet();
                session.play();
                if (session.getWinningAmount() == 0) {
                    assertEquals(session.getCreditsAmount(), lastCredits - lastBet);
                }
            }
            assertTrue(session.getCreditsAmount() >= lastCredits - lastBet);

            wasLinesWin = session.getWinningLines().size() > 0;
            wasScattersWin = session.getWinningScatters().size() > 0;
            if (i == timesToPlay - 1 && !wasLinesWin && !wasScattersWin) {
                i = 0;
            }
        }
    }

    @Test
    void passBaseTests() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        ReelGameSessionImpl sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        GameSessionImplTest.testDefaultSessionHasProperInitialValues(sess, conf);

        GameSessionConfig baseConf = GameSessionImplTest.createCustomConfigForTestProperInitialValues();
        conf = new DefaultReelGameSessionConfig();
        conf.setAvailableBets(baseConf.getAvailableBets());
        conf.setCreditsAmount(baseConf.getCreditsAmount());
        sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        GameSessionImplTest.testDefaultSessionHasProperInitialValuesWithCustomConfig(sess, conf);

        baseConf = GameSessionImplTest.createCustomConfigForWrongBetTest();
        conf = new DefaultReelGameSessionConfig();
        conf.setAvailableBets(baseConf.getAvailableBets());
        sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        GameSessionImplTest.testDefaultSessionWithWrongInitialBet(sess, conf);
    }

    @Test
    void testCreateNewSession() {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        ReelGameSessionImpl sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        testDefaultReelGameSessionHasProperInitialValues(sess, conf);
    }

    @Test
    void testPlaySeveralTimesUntilAnyWinning() throws Exception {
        ReelGameSessionConfig conf = new DefaultReelGameSessionConfig();
        conf.setCreditsAmount(10000000);
        ReelGameSessionImpl sess = new ReelGameSessionImpl(conf, new ReelGameSessionReelsControllerImpl(conf), new ReelGameSessionWinCalculatorImpl(conf));
        testPlayUntilWin(sess, conf);
    }

}