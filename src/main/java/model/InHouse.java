package model;
/**
 * This class creates a part type, in-house
 *
 * @author Ella Upchurch
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Creates new instance of in-house part
     *
     * @param id part ID
     * @param name part name
     * @param price part price
     * @param stock part inventory level
     * @param min part minimum
     * @param max part maximum
     * @param machineId part machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** The getter for part machine ID
     * @return machine ID of part
     */
    public int getMachineId() {return machineId;}

    /** The setter for part machine ID
     * @param machineId
     */
    public void setMachineId(int machineId) {this.machineId = machineId;}
}
