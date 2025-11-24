package ed.u2.models;

//Clase de modelo para representar un ítem de inventario. Se utiliza para almacenar los datos cargados desde CSV.
public class InventoryItem {

    private final String id;
    private final String insumo;
    private final int stock;

    public InventoryItem(String id, String insumo, int stock) {
        this.id = id;
        this.insumo = insumo;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getInsumo() { return insumo; }
    public int getStock() { return stock; } // Getter principal usado para ordenar por cantidad.

    @Override
    public String toString() {
        return "%s (Stock: %d)".formatted(insumo, stock);
    }
}
