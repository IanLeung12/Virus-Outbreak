public class Neighborhood {
    private String type;
    private boolean vaccineAdministered;
    private int typeCycles;
    private int x;
    private int y;
    Neighborhood(String type, int x, int y) {
        this.type = type;
        this.vaccineAdministered = false;
        this.typeCycles = 0;
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVaccineAdministered() {
        return vaccineAdministered;
    }

    public void setVaccineAdministered(boolean vaccineAdministered) {
        this.vaccineAdministered = vaccineAdministered;
    }

    public int getTypeCycles() {
        return typeCycles;
    }

    public void setTypeCycles(int typeCycles) {
        this.typeCycles = typeCycles;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
