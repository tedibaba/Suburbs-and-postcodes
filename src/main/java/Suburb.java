public class Suburb {

    static private String suburb = "";
    static private String postcode = "";

    public Suburb(String suburb, String postcode){
        this.suburb = suburb;
        this.postcode = postcode;
    }

    public static String getSuburb() {
        return suburb;
    }

    public static void setSuburb(String suburb) {
        Suburb.suburb = suburb;
    }

    public static String getPostcode() {
        return postcode;
    }

    public static void setPostcode(String postcode) {
        Suburb.postcode = postcode;
    }
}
