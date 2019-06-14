package slotify4j.session.videogames.reelgames;

import slotify4j.session.DefaultGameSessionConfig;

import java.util.stream.Stream;

public class DefaultReelGameSessionConfig extends DefaultGameSessionConfig implements ReelGameSessionConfig {
    private int reelsNumber;
    private int reelsItemsNumber;
    private String[] availableItems;
    private String wildItemId;
    private String[][] reelsItemsSequences;
    private ReelGameSessionPaytableData paytable;
    private ReelGameSessionScatterData[] scatters;
    private ReelGameSessionLinesDirectionData linesDirections;
    private ReelGameSessionWildsMultipliersData wildsMultipliers;

    public DefaultReelGameSessionConfig() {
        reelsNumber = DEFAULT_REELS_NUMBER;
        reelsItemsNumber = DEFAULT_REELS_ITEMS_NUMBER;
        availableItems = DEFAULT_AVAILABLE_ITEMS;
        wildItemId = DEFAULT_WILD_ITEM_ID;
        paytable = new ReelGameSessionPaytableDataImpl(ReelGameSessionPaytableData.createDefaultPaytableMap(getAvailableBets(), availableItems, reelsNumber, wildItemId));
        scatters = new ReelGameSessionScatterData[]{new ReelGameSessionScattersDataImpl(DEFAULT_SCATTER_ITEM_ID, DEFAULT_MINIMUM_ITEMS_NUM_FOR_SCATTER_WIN)};
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(ReelGameSessionLinesDirectionData.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber));
        wildsMultipliers = new ReelGameSessionWildsMultipliersDataPowerOfTwo();
        reelsItemsSequences = ReelGameSessionConfig.createReelsItemsSequences(reelsNumber, availableItems);
    }

    @Override
    public ReelGameSessionPaytableData getPaytable() {
        return paytable;
    }

    @Override
    public void setPaytable(ReelGameSessionPaytableData paytable) {
        this.paytable = paytable;
    }

    @Override
    public String getWildItemId() {
        return wildItemId;
    }

    @Override
    public void setWildItemId(String wildItemId) {
        this.wildItemId = wildItemId;
    }

    @Override
    public ReelGameSessionScatterData[] getScattersData() {
        return scatters;
    }

    @Override
    public void setScattersData(ReelGameSessionScatterData[] scattersData) {
        this.scatters = scattersData;
    }

    @Override
    public ReelGameSessionLinesDirectionData getLinesDirections() {
        return linesDirections;
    }

    @Override
    public void setLinesDirections(ReelGameSessionLinesDirectionData linesDirections) {
        this.linesDirections = linesDirections;
    }

    @Override
    public ReelGameSessionWildsMultipliersData getWildsMultipliers() {
        return wildsMultipliers;
    }

    @Override
    public void setWildsMultipliers(ReelGameSessionWildsMultipliersData wildsMultipliers) {
        this.wildsMultipliers = wildsMultipliers;
    }

    @Override
    public int getReelsItemsNumber() {
        return reelsItemsNumber;
    }

    @Override
    public void setReelsItemsNumber(int reelsItemsNumber) {
        this.reelsItemsNumber = reelsItemsNumber;
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(ReelGameSessionLinesDirectionData.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber));
    }

    @Override
    public int getReelsNumber() {
        return reelsNumber;
    }

    @Override
    public void setReelsNumber(int reelsItemsNumber) {
        this.reelsNumber = reelsItemsNumber;
        linesDirections = new ReelGameSessionLinesDirectionDataImpl(ReelGameSessionLinesDirectionData.createDefaultLinesDirectionsMap(reelsNumber, reelsItemsNumber));
    }

    @Override
    public String[] getAvailableItems() {
        return availableItems;
    }

    @Override
    public void setAvailableItems(String[] availableItems) {
        this.availableItems = availableItems;
        paytable = new ReelGameSessionPaytableDataImpl(ReelGameSessionPaytableData.createDefaultPaytableMap(getAvailableBets(), availableItems, reelsNumber, wildItemId));
        reelsItemsSequences = ReelGameSessionConfig.createReelsItemsSequences(reelsNumber, availableItems);
    }

    @Override
    public String[][] getReelsItemsSequences() {
        return reelsItemsSequences;
    }

    @Override
    public void setReelsItemsSequences(String[][] reelsItemsSequences) {
        this.reelsItemsSequences = reelsItemsSequences;
    }

    @Override
    public boolean isItemWild(String itemId) {
        return itemId.equals(wildItemId);
    }

    @Override
    public boolean isItemScatter(String itemId) {
        return Stream.of(scatters).anyMatch(s -> s.getItemId().equals(itemId));
    }

}
