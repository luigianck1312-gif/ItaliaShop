package it.italiashop.models;

import org.bukkit.Material;

public class ShopItem {

    public enum Category {
        BLOCCHI, MINERALI, NATURA, NETHER, END, RARI
    }

    private final Material material;
    private final String displayName;
    private final Category category;
    private double basePrice;
    private double baseSellPrice;
    private final double minPrice;
    private final double maxPrice;
    private final double minSellPrice;
    private final double maxSellPrice;
    private double currentPrice;
    private double currentSellPrice;
    private int buyCount; // quante volte comprato dall'ultimo reset
    private int sellCount; // quante volte venduto dall'ultimo reset
    private final boolean rare;
    private final int cooldownDays; // 0 = nessun cooldown
    private final int maxStack; // quanti se ne possono comprare alla volta

    public ShopItem(Material material, String displayName, Category category,
                    double basePrice, double baseSellPrice,
                    double minPrice, double maxPrice,
                    boolean rare, int cooldownDays, int maxStack) {
        this.material = material;
        this.displayName = displayName;
        this.category = category;
        this.basePrice = basePrice;
        this.baseSellPrice = baseSellPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minSellPrice = minSellPrice(baseSellPrice);
        this.maxSellPrice = maxSellPrice(baseSellPrice);
        this.currentPrice = basePrice;
        this.currentSellPrice = baseSellPrice;
        this.rare = rare;
        this.cooldownDays = cooldownDays;
        this.maxStack = maxStack;
    }

    private double minSellPrice(double base) { return base * 0.3; }
    private double maxSellPrice(double base) { return base * 1.5; }

    public void onBuy(int amount) {
        buyCount += amount;
        // Ogni 64 comprati il prezzo sale del 2%
        double factor = 1.0 + (buyCount / 64.0) * 0.02;
        currentPrice = Math.min(basePrice * factor, maxPrice);
        // La vendita sale un po' meno
        currentSellPrice = Math.min(baseSellPrice * (1.0 + (buyCount / 64.0) * 0.01), maxSellPrice);
    }

    public void onSell(int amount) {
        sellCount += amount;
        // Ogni 64 venduti il prezzo scende del 2%
        double factor = 1.0 - (sellCount / 64.0) * 0.02;
        currentPrice = Math.max(basePrice * factor, minPrice);
        currentSellPrice = Math.max(baseSellPrice * (1.0 - (sellCount / 64.0) * 0.01), minSellPrice);
    }

    public void resetPrices() {
        buyCount = 0;
        sellCount = 0;
        currentPrice = basePrice;
        currentSellPrice = baseSellPrice;
    }

    public Material getMaterial() { return material; }
    public String getDisplayName() { return displayName; }
    public Category getCategory() { return category; }
    public double getCurrentPrice() { return currentPrice; }
    public double getCurrentSellPrice() { return currentSellPrice; }
    public double getMinPrice() { return minPrice; }
    public double getMaxPrice() { return maxPrice; }
    public boolean isRare() { return rare; }
    public int getCooldownDays() { return cooldownDays; }
    public int getMaxStack() { return maxStack; }
    public double getBasePrice() { return basePrice; }
    public double getBaseSellPrice() { return baseSellPrice; }
}
