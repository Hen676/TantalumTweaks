package hen676.dragonlite.config;

public class Config {
    // Compass settings
    public static boolean ENABLE_COMPASS = false;
    public static double COMPASS_COLOR_RED = 0.7D;
    public static double COMPASS_COLOR_GREEN = 0.1D;
    public static double COMPASS_COLOR_BLUE = 0.1D;
    public static int COMPASS_PLACEMENT = 0;
    public static double COMPASS_SCALE = 1.0D;

    // Zoom config settings
    public static double ZOOM_AMOUNT = 0.35D;

    // Reduce fog settings
    public static boolean ENABLE_REDUCED_FOG = true;

    // Light level settings
    public static double LIGHT_LEVEL_ALPHA = 0.3D;
    public static double LIGHT_LEVEL_COLOR_RED = 1.0D;
    public static double LIGHT_LEVEL_COLOR_GREEN = 0.0D;
    public static double LIGHT_LEVEL_COLOR_BLUE = 0.0D;
    public static double LIGHT_LEVEL_SQUARE_SIZE = 0.25D;

    // Smokey furnace settings
    public static boolean ENABLE_SMOKEY_FURNACE = true;

    // Freecam settings
    public static double FREECAM_FLIGHT_SPEED = 0.5D;

    // Full bright settings
    public static boolean ENABLE_FULL_BRIGHT_ON_FREECAM = true;

    // Recipe Book settings
    public static boolean ON_INIT_OPEN_RECIPE_BOOK_FOR_CRAFTING = true;
    public static boolean ON_INIT_OPEN_RECIPE_BOOK_FOR_INVENTORY = false;
    public static boolean ENABLE_CUSTOM_SPLIT_RECIPE_BOOK_FOR_CRAFTING = true;
}
