package github.ricemonger.marketplace.graphs.database.neo4j.enums;

public enum UserPlatform {
    UPLAY,
    PLAYSTATION, PSN,
    XBOX, XBL,
    XPLAY;

    public final static String UPLAY_SPACE_ID = "5172a557-50b5-4665-b7db-e3f2e8c5041d";

    public final static String PSN_SPACE_ID = "05bfb3f7-6c21-4c42-be1f-97a33fb5cf66";

    public final static String XBL_SPACE_ID = "98a601e5-ca91-4440-b1c5-753f601a2c90";

    public final static String XPLAY_SPACE_ID = "0d2ae42d-4c27-4cb7-af6c-2099062302bb";

    public String getSpaceId(){
        switch(this){
            case UPLAY:
                return UPLAY_SPACE_ID;
            case PSN, PLAYSTATION:
                return PSN_SPACE_ID;
            case XBL, XBOX:
                return XBL_SPACE_ID;
            case XPLAY:
                return XPLAY_SPACE_ID;
            default:
                throw new IllegalArgumentException("Invalid platform: " + this);
        }
    }

    public static String getSpaceId(UserPlatform platform){
        return platform.getSpaceId();
    }
}
