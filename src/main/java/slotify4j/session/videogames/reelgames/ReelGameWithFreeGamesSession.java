package slotify4j.session.videogames.reelgames;

public interface ReelGameWithFreeGamesSession extends ReelGameSession {

    int getWonFreeGamesNumber();

    int getFreeGameNum();

    void setFreeGameNum(int value);

    int getFreeGameSum();

    void setFreeGameSum(int value);

    int getFreeGameBank();

    void setFreeGameBank(int value);

}
