package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import it.italiashop.models.ShopItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ShopManager {

    private final ItaliaShop plugin;
    private final List<ShopItem> items = new ArrayList<>();
    private final Map<UUID, Map<Material, Long>> cooldowns = new HashMap<>();

    public ShopManager(ItaliaShop plugin) {
        this.plugin = plugin;
        registerItems();
        loadPrices();
        long threeDays = 20L * 60 * 60 * 24 * 3;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::resetAllPrices, threeDays, threeDays);
    }

    // buy = prezzo acquisto, sell calcolato auto al 60%
    private void add(Material mat, String name, ShopItem.Category cat, double buy, boolean rare, int cooldown, int maxStack) {
        double sell = buy * 0.6;
        double min = buy * 0.3;
        double max = buy * 2.5;
        items.add(new ShopItem(mat, name, cat, buy, sell, min, max, rare, cooldown, maxStack, true));
    }

    private void add(Material mat, String name, ShopItem.Category cat, double buy) {
        add(mat, name, cat, buy, false, 0, 64);
    }

    private void registerItems() {

        // ============================================================
        // BLOCCHI BASE (prezzi acquisto = vendita/0.6)
        // ============================================================
        add(Material.DIRT, "Terra", ShopItem.Category.BLOCCHI, 6700);
        add(Material.GRASS_BLOCK, "Erba", ShopItem.Category.BLOCCHI, 7000);
        add(Material.COARSE_DIRT, "Terra Grezza", ShopItem.Category.BLOCCHI, 7000);
        add(Material.PODZOL, "Podzol", ShopItem.Category.BLOCCHI, 8000);
        add(Material.ROOTED_DIRT, "Terra Radicata", ShopItem.Category.BLOCCHI, 9000);
        add(Material.MUD, "Fango", ShopItem.Category.BLOCCHI, 8000);
        add(Material.PACKED_MUD, "Fango Compatto", ShopItem.Category.BLOCCHI, 9000);
        add(Material.MUD_BRICKS, "Mattoni Fango", ShopItem.Category.BLOCCHI, 11500);
        add(Material.MUD_BRICK_STAIRS, "Scale Fango", ShopItem.Category.BLOCCHI, 12500);
        add(Material.MUD_BRICK_SLAB, "Lastra Fango", ShopItem.Category.BLOCCHI, 10500);
        add(Material.MUD_BRICK_WALL, "Muro Fango", ShopItem.Category.BLOCCHI, 12000);
        add(Material.SAND, "Sabbia", ShopItem.Category.BLOCCHI, 6000);
        add(Material.RED_SAND, "Sabbia Rossa", ShopItem.Category.BLOCCHI, 7000);
        add(Material.GRAVEL, "Ghiaia", ShopItem.Category.BLOCCHI, 5500);
        add(Material.CLAY, "Argilla", ShopItem.Category.BLOCCHI, 8000);
        add(Material.CLAY_BALL, "Palla Argilla", ShopItem.Category.BLOCCHI, 2200);
        add(Material.ICE, "Ghiaccio", ShopItem.Category.BLOCCHI, 8000);
        add(Material.PACKED_ICE, "Ghiaccio Compatto", ShopItem.Category.BLOCCHI, 16000);
        add(Material.BLUE_ICE, "Ghiaccio Blu", ShopItem.Category.BLOCCHI, 40000);
        add(Material.SNOW_BLOCK, "Blocco Neve", ShopItem.Category.BLOCCHI, 5500);
        add(Material.SNOW, "Neve", ShopItem.Category.BLOCCHI, 1400);
        add(Material.OBSIDIAN, "Ossidiana", ShopItem.Category.BLOCCHI, 32000);
        add(Material.CRYING_OBSIDIAN, "Ossidiana Piangente", ShopItem.Category.BLOCCHI, 53500);
        add(Material.COBWEB, "Ragnatela", ShopItem.Category.BLOCCHI, 21500);
        add(Material.SPONGE, "Spugna", ShopItem.Category.BLOCCHI, 80000);
        add(Material.DRIPSTONE_BLOCK, "Blocco Dripstone", ShopItem.Category.BLOCCHI, 8000);
        add(Material.POINTED_DRIPSTONE, "Stalattite", ShopItem.Category.BLOCCHI, 3200);
        add(Material.MYCELIUM, "Micelio", ShopItem.Category.BLOCCHI, 10700);

        // ============================================================
        // PIETRA & ROCCIA
        // Schema: base=6700, polished=+1300, bricks=+4000, stairs=+800, slab=-500, wall=+500
        // ============================================================
        add(Material.STONE, "Pietra", ShopItem.Category.PIETRA, 6700);
        add(Material.SMOOTH_STONE, "Pietra Liscia", ShopItem.Category.PIETRA, 8000);
        add(Material.COBBLESTONE, "Ciottoli", ShopItem.Category.PIETRA, 5000);
        add(Material.MOSSY_COBBLESTONE, "Ciottoli Muschiati", ShopItem.Category.PIETRA, 7000);
        add(Material.STONE_BRICKS, "Mattoni Pietra", ShopItem.Category.PIETRA, 11000);
        add(Material.MOSSY_STONE_BRICKS, "Mattoni Muschiati", ShopItem.Category.PIETRA, 13000);
        add(Material.CRACKED_STONE_BRICKS, "Mattoni Incrinati", ShopItem.Category.PIETRA, 12000);
        add(Material.CHISELED_STONE_BRICKS, "Mattoni Cesellati", ShopItem.Category.PIETRA, 13000);
        add(Material.STONE_STAIRS, "Scale Pietra", ShopItem.Category.PIETRA, 7500);
        add(Material.STONE_SLAB, "Lastra Pietra", ShopItem.Category.PIETRA, 6200);
        add(Material.SMOOTH_STONE_SLAB, "Lastra Liscia", ShopItem.Category.PIETRA, 7500);
        add(Material.STONE_BRICK_STAIRS, "Scale Mattoni Pietra", ShopItem.Category.PIETRA, 12000);
        add(Material.STONE_BRICK_SLAB, "Lastra Mattoni Pietra", ShopItem.Category.PIETRA, 9500);
        add(Material.STONE_BRICK_WALL, "Muro Mattoni Pietra", ShopItem.Category.PIETRA, 11500);
        add(Material.COBBLESTONE_STAIRS, "Scale Ciottoli", ShopItem.Category.PIETRA, 5800);
        add(Material.COBBLESTONE_SLAB, "Lastra Ciottoli", ShopItem.Category.PIETRA, 4500);
        add(Material.COBBLESTONE_WALL, "Muro Ciottoli", ShopItem.Category.PIETRA, 5500);
        add(Material.MOSSY_COBBLESTONE_STAIRS, "Scale Muschio", ShopItem.Category.PIETRA, 7800);
        add(Material.MOSSY_COBBLESTONE_SLAB, "Lastra Muschio", ShopItem.Category.PIETRA, 6500);
        add(Material.MOSSY_COBBLESTONE_WALL, "Muro Muschio", ShopItem.Category.PIETRA, 7500);
        add(Material.MOSSY_STONE_BRICK_STAIRS, "Scale Mattoni Muschio", ShopItem.Category.PIETRA, 13800);
        add(Material.MOSSY_STONE_BRICK_SLAB, "Lastra Mattoni Muschio", ShopItem.Category.PIETRA, 12500);
        add(Material.MOSSY_STONE_BRICK_WALL, "Muro Mattoni Muschio", ShopItem.Category.PIETRA, 13500);
        add(Material.BRICKS, "Mattoni", ShopItem.Category.PIETRA, 11000);
        add(Material.BRICK_STAIRS, "Scale Mattoni", ShopItem.Category.PIETRA, 11800);
        add(Material.BRICK_SLAB, "Lastra Mattoni", ShopItem.Category.PIETRA, 10500);
        add(Material.BRICK_WALL, "Muro Mattoni", ShopItem.Category.PIETRA, 11500);
        // Sandstone
        add(Material.SANDSTONE, "Arenaria", ShopItem.Category.PIETRA, 6700);
        add(Material.SMOOTH_SANDSTONE, "Arenaria Liscia", ShopItem.Category.PIETRA, 8000);
        add(Material.CHISELED_SANDSTONE, "Arenaria Cesellata", ShopItem.Category.PIETRA, 9000);
        add(Material.CUT_SANDSTONE, "Arenaria Tagliata", ShopItem.Category.PIETRA, 8000);
        add(Material.SANDSTONE_STAIRS, "Scale Arenaria", ShopItem.Category.PIETRA, 7500);
        add(Material.SANDSTONE_SLAB, "Lastra Arenaria", ShopItem.Category.PIETRA, 6200);
        add(Material.SANDSTONE_WALL, "Muro Arenaria", ShopItem.Category.PIETRA, 7200);
        add(Material.RED_SANDSTONE, "Arenaria Rossa", ShopItem.Category.PIETRA, 7000);
        add(Material.SMOOTH_RED_SANDSTONE, "Arenaria Rossa Liscia", ShopItem.Category.PIETRA, 8300);
        add(Material.CHISELED_RED_SANDSTONE, "Arenaria Rossa Cesellata", ShopItem.Category.PIETRA, 9300);
        add(Material.CUT_RED_SANDSTONE, "Arenaria Rossa Tagliata", ShopItem.Category.PIETRA, 8300);
        add(Material.RED_SANDSTONE_STAIRS, "Scale Arenaria Rossa", ShopItem.Category.PIETRA, 7800);
        add(Material.RED_SANDSTONE_SLAB, "Lastra Arenaria Rossa", ShopItem.Category.PIETRA, 6500);
        add(Material.RED_SANDSTONE_WALL, "Muro Arenaria Rossa", ShopItem.Category.PIETRA, 7500);
        // Andesite
        add(Material.ANDESITE, "Andesite", ShopItem.Category.PIETRA, 6700);
        add(Material.POLISHED_ANDESITE, "Andesite Levigata", ShopItem.Category.PIETRA, 8000);
        add(Material.ANDESITE_STAIRS, "Scale Andesite", ShopItem.Category.PIETRA, 7500);
        add(Material.ANDESITE_SLAB, "Lastra Andesite", ShopItem.Category.PIETRA, 6200);
        add(Material.ANDESITE_WALL, "Muro Andesite", ShopItem.Category.PIETRA, 7200);
        add(Material.POLISHED_ANDESITE_STAIRS, "Scale Andesite Lev.", ShopItem.Category.PIETRA, 8800);
        add(Material.POLISHED_ANDESITE_SLAB, "Lastra Andesite Lev.", ShopItem.Category.PIETRA, 7500);
        // Diorite
        add(Material.DIORITE, "Diorite", ShopItem.Category.PIETRA, 6700);
        add(Material.POLISHED_DIORITE, "Diorite Levigata", ShopItem.Category.PIETRA, 8000);
        add(Material.DIORITE_STAIRS, "Scale Diorite", ShopItem.Category.PIETRA, 7500);
        add(Material.DIORITE_SLAB, "Lastra Diorite", ShopItem.Category.PIETRA, 6200);
        add(Material.DIORITE_WALL, "Muro Diorite", ShopItem.Category.PIETRA, 7200);
        add(Material.POLISHED_DIORITE_STAIRS, "Scale Diorite Lev.", ShopItem.Category.PIETRA, 8800);
        add(Material.POLISHED_DIORITE_SLAB, "Lastra Diorite Lev.", ShopItem.Category.PIETRA, 7500);
        // Granite
        add(Material.GRANITE, "Granito", ShopItem.Category.PIETRA, 6700);
        add(Material.POLISHED_GRANITE, "Granito Levigato", ShopItem.Category.PIETRA, 8000);
        add(Material.GRANITE_STAIRS, "Scale Granito", ShopItem.Category.PIETRA, 7500);
        add(Material.GRANITE_SLAB, "Lastra Granito", ShopItem.Category.PIETRA, 6200);
        add(Material.GRANITE_WALL, "Muro Granito", ShopItem.Category.PIETRA, 7200);
        add(Material.POLISHED_GRANITE_STAIRS, "Scale Granito Lev.", ShopItem.Category.PIETRA, 8800);
        add(Material.POLISHED_GRANITE_SLAB, "Lastra Granito Lev.", ShopItem.Category.PIETRA, 7500);
        // Deepslate
        add(Material.DEEPSLATE, "Deepslate", ShopItem.Category.PIETRA, 7000);
        add(Material.COBBLED_DEEPSLATE, "Deepslate Ciottoli", ShopItem.Category.PIETRA, 6000);
        add(Material.POLISHED_DEEPSLATE, "Deepslate Levigata", ShopItem.Category.PIETRA, 8300);
        add(Material.DEEPSLATE_BRICKS, "Mattoni Deepslate", ShopItem.Category.PIETRA, 11500);
        add(Material.DEEPSLATE_TILES, "Piastrelle Deepslate", ShopItem.Category.PIETRA, 12500);
        add(Material.CHISELED_DEEPSLATE, "Deepslate Cesellata", ShopItem.Category.PIETRA, 13000);
        add(Material.CRACKED_DEEPSLATE_BRICKS, "Mattoni Deepslate Inc.", ShopItem.Category.PIETRA, 12000);
        add(Material.CRACKED_DEEPSLATE_TILES, "Piastrelle Deepslate Inc.", ShopItem.Category.PIETRA, 13000);
        add(Material.COBBLED_DEEPSLATE_STAIRS, "Scale Deepslate", ShopItem.Category.PIETRA, 6800);
        add(Material.COBBLED_DEEPSLATE_SLAB, "Lastra Deepslate", ShopItem.Category.PIETRA, 5500);
        add(Material.COBBLED_DEEPSLATE_WALL, "Muro Deepslate", ShopItem.Category.PIETRA, 6500);
        add(Material.POLISHED_DEEPSLATE_STAIRS, "Scale Deepslate Lev.", ShopItem.Category.PIETRA, 9100);
        add(Material.POLISHED_DEEPSLATE_SLAB, "Lastra Deepslate Lev.", ShopItem.Category.PIETRA, 7800);
        add(Material.POLISHED_DEEPSLATE_WALL, "Muro Deepslate Lev.", ShopItem.Category.PIETRA, 8800);
        add(Material.DEEPSLATE_BRICK_STAIRS, "Scale Mattoni Deep.", ShopItem.Category.PIETRA, 12300);
        add(Material.DEEPSLATE_BRICK_SLAB, "Lastra Mattoni Deep.", ShopItem.Category.PIETRA, 11000);
        add(Material.DEEPSLATE_BRICK_WALL, "Muro Mattoni Deep.", ShopItem.Category.PIETRA, 12000);
        add(Material.DEEPSLATE_TILE_STAIRS, "Scale Piastrelle Deep.", ShopItem.Category.PIETRA, 13300);
        add(Material.DEEPSLATE_TILE_SLAB, "Lastra Piastrelle Deep.", ShopItem.Category.PIETRA, 12000);
        add(Material.DEEPSLATE_TILE_WALL, "Muro Piastrelle Deep.", ShopItem.Category.PIETRA, 13000);
        // Tuff
        add(Material.TUFF, "Tuff", ShopItem.Category.PIETRA, 6700);
        add(Material.POLISHED_TUFF, "Tuff Levigato", ShopItem.Category.PIETRA, 8000);
        add(Material.CHISELED_TUFF, "Tuff Cesellato", ShopItem.Category.PIETRA, 9000);
        add(Material.TUFF_BRICKS, "Mattoni Tuff", ShopItem.Category.PIETRA, 11000);
        add(Material.CHISELED_TUFF_BRICKS, "Mattoni Tuff Ces.", ShopItem.Category.PIETRA, 12000);
        add(Material.TUFF_STAIRS, "Scale Tuff", ShopItem.Category.PIETRA, 7500);
        add(Material.TUFF_SLAB, "Lastra Tuff", ShopItem.Category.PIETRA, 6200);
        add(Material.TUFF_WALL, "Muro Tuff", ShopItem.Category.PIETRA, 7200);
        add(Material.POLISHED_TUFF_STAIRS, "Scale Tuff Lev.", ShopItem.Category.PIETRA, 8800);
        add(Material.POLISHED_TUFF_SLAB, "Lastra Tuff Lev.", ShopItem.Category.PIETRA, 7500);
        add(Material.POLISHED_TUFF_WALL, "Muro Tuff Lev.", ShopItem.Category.PIETRA, 8500);
        add(Material.TUFF_BRICK_STAIRS, "Scale Mattoni Tuff", ShopItem.Category.PIETRA, 11800);
        add(Material.TUFF_BRICK_SLAB, "Lastra Mattoni Tuff", ShopItem.Category.PIETRA, 10500);
        add(Material.TUFF_BRICK_WALL, "Muro Mattoni Tuff", ShopItem.Category.PIETRA, 11500);
        // Blackstone
        add(Material.BLACKSTONE, "Blackstone", ShopItem.Category.PIETRA, 7500);
        add(Material.GILDED_BLACKSTONE, "Blackstone Dorata", ShopItem.Category.PIETRA, 15000);
        add(Material.POLISHED_BLACKSTONE, "Blackstone Lev.", ShopItem.Category.PIETRA, 8800);
        add(Material.CHISELED_POLISHED_BLACKSTONE, "Blackstone Ces.", ShopItem.Category.PIETRA, 10000);
        add(Material.POLISHED_BLACKSTONE_BRICKS, "Mattoni Blackstone", ShopItem.Category.PIETRA, 12000);
        add(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, "Mattoni Black. Inc.", ShopItem.Category.PIETRA, 13000);
        add(Material.BLACKSTONE_STAIRS, "Scale Blackstone", ShopItem.Category.PIETRA, 8300);
        add(Material.BLACKSTONE_SLAB, "Lastra Blackstone", ShopItem.Category.PIETRA, 7000);
        add(Material.BLACKSTONE_WALL, "Muro Blackstone", ShopItem.Category.PIETRA, 8000);
        add(Material.POLISHED_BLACKSTONE_STAIRS, "Scale Blackstone Lev.", ShopItem.Category.PIETRA, 9600);
        add(Material.POLISHED_BLACKSTONE_SLAB, "Lastra Blackstone Lev.", ShopItem.Category.PIETRA, 8300);
        add(Material.POLISHED_BLACKSTONE_WALL, "Muro Blackstone Lev.", ShopItem.Category.PIETRA, 9300);
        add(Material.POLISHED_BLACKSTONE_BRICK_STAIRS, "Scale Mattoni Black.", ShopItem.Category.PIETRA, 12800);
        add(Material.POLISHED_BLACKSTONE_BRICK_SLAB, "Lastra Mattoni Black.", ShopItem.Category.PIETRA, 11500);
        add(Material.POLISHED_BLACKSTONE_BRICK_WALL, "Muro Mattoni Black.", ShopItem.Category.PIETRA, 12500);
        add(Material.POLISHED_BLACKSTONE_BUTTON, "Pulsante Blackstone", ShopItem.Category.PIETRA, 4000);
        add(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, "Piattaforma Blackstone", ShopItem.Category.PIETRA, 5300);
        // Prismarine
        add(Material.PRISMARINE, "Prismarina", ShopItem.Category.PIETRA, 15000);
        add(Material.PRISMARINE_BRICKS, "Mattoni Prismarina", ShopItem.Category.PIETRA, 20000);
        add(Material.DARK_PRISMARINE, "Prismarina Scura", ShopItem.Category.PIETRA, 25000);
        add(Material.SEA_LANTERN, "Lanterna Marina", ShopItem.Category.PIETRA, 30000);
        add(Material.PRISMARINE_STAIRS, "Scale Prismarina", ShopItem.Category.PIETRA, 15800);
        add(Material.PRISMARINE_SLAB, "Lastra Prismarina", ShopItem.Category.PIETRA, 14500);
        add(Material.PRISMARINE_BRICK_STAIRS, "Scale Mattoni Pris.", ShopItem.Category.PIETRA, 20800);
        add(Material.PRISMARINE_BRICK_SLAB, "Lastra Mattoni Pris.", ShopItem.Category.PIETRA, 19500);
        add(Material.DARK_PRISMARINE_STAIRS, "Scale Pris. Scura", ShopItem.Category.PIETRA, 25800);
        add(Material.DARK_PRISMARINE_SLAB, "Lastra Pris. Scura", ShopItem.Category.PIETRA, 24500);
        add(Material.PRISMARINE_SHARD, "Frammento Prismarina", ShopItem.Category.PIETRA, 5000);
        add(Material.PRISMARINE_CRYSTALS, "Cristalli Prismarina", ShopItem.Category.PIETRA, 8000);
        // Basalt/Calcite
        add(Material.BASALT, "Basalto", ShopItem.Category.PIETRA, 7000);
        add(Material.SMOOTH_BASALT, "Basalto Liscio", ShopItem.Category.PIETRA, 8300);
        add(Material.POLISHED_BASALT, "Basalto Levigato", ShopItem.Category.PIETRA, 8300);
        add(Material.CALCITE, "Calcite", ShopItem.Category.PIETRA, 6700);

        // ============================================================
        // LEGNO
        // ============================================================
        String[] woodTypes = {"OAK","BIRCH","SPRUCE","JUNGLE","ACACIA","DARK_OAK","MANGROVE","CHERRY"};
        String[] woodNames = {"Quercia","Betulla","Abete","Giungla","Acacia","Rovere","Mangrovia","Ciliegio"};
        for (int i = 0; i < woodTypes.length; i++) {
            String w = woodTypes[i]; String n = woodNames[i];
            try { add(Material.valueOf(w+"_LOG"), "Tronco "+n, ShopItem.Category.LEGNO, 8000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_WOOD"), "Legno "+n, ShopItem.Category.LEGNO, 8500); } catch(Exception e){}
            try { add(Material.valueOf("STRIPPED_"+w+"_LOG"), "Tronco Scort. "+n, ShopItem.Category.LEGNO, 8500); } catch(Exception e){}
            try { add(Material.valueOf(w+"_PLANKS"), "Assi "+n, ShopItem.Category.LEGNO, 9000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_STAIRS"), "Scale "+n, ShopItem.Category.LEGNO, 10000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_SLAB"), "Lastra "+n, ShopItem.Category.LEGNO, 8000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_FENCE"), "Staccionata "+n, ShopItem.Category.LEGNO, 11000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_FENCE_GATE"), "Cancello "+n, ShopItem.Category.LEGNO, 11500); } catch(Exception e){}
            try { add(Material.valueOf(w+"_DOOR"), "Porta "+n, ShopItem.Category.LEGNO, 13000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_TRAPDOOR"), "Botola "+n, ShopItem.Category.LEGNO, 11500); } catch(Exception e){}
            try { add(Material.valueOf(w+"_PRESSURE_PLATE"), "Piattaforma "+n, ShopItem.Category.LEGNO, 5300); } catch(Exception e){}
            try { add(Material.valueOf(w+"_BUTTON"), "Pulsante "+n, ShopItem.Category.LEGNO, 4000); } catch(Exception e){}
            try { add(Material.valueOf(w+"_SIGN"), "Cartello "+n, ShopItem.Category.LEGNO, 6700); } catch(Exception e){}
            try { add(Material.valueOf(w+"_LEAVES"), "Foglie "+n, ShopItem.Category.LEGNO, 3200); } catch(Exception e){}
            try { add(Material.valueOf(w+"_SAPLING"), "Germoglio "+n, ShopItem.Category.LEGNO, 5300); } catch(Exception e){}
            try { add(Material.valueOf(w+"_BOAT"), "Barca "+n, ShopItem.Category.LEGNO, 16000, false, 0, 1); } catch(Exception e){}
        }
        add(Material.BAMBOO, "Bambu", ShopItem.Category.LEGNO, 8000);
        add(Material.BAMBOO_BLOCK, "Blocco Bambu", ShopItem.Category.LEGNO, 9000);
        add(Material.BAMBOO_PLANKS, "Assi Bambu", ShopItem.Category.LEGNO, 9000);
        add(Material.BAMBOO_MOSAIC, "Mosaico Bambu", ShopItem.Category.LEGNO, 10000);
        add(Material.BAMBOO_STAIRS, "Scale Bambu", ShopItem.Category.LEGNO, 10000);
        add(Material.BAMBOO_SLAB, "Lastra Bambu", ShopItem.Category.LEGNO, 8000);
        add(Material.BAMBOO_FENCE, "Staccionata Bambu", ShopItem.Category.LEGNO, 11000);
        add(Material.BAMBOO_FENCE_GATE, "Cancello Bambu", ShopItem.Category.LEGNO, 11500);
        add(Material.BAMBOO_DOOR, "Porta Bambu", ShopItem.Category.LEGNO, 13000);
        add(Material.BAMBOO_TRAPDOOR, "Botola Bambu", ShopItem.Category.LEGNO, 11500);
        add(Material.BAMBOO_PRESSURE_PLATE, "Piattaforma Bambu", ShopItem.Category.LEGNO, 5300);
        add(Material.BAMBOO_BUTTON, "Pulsante Bambu", ShopItem.Category.LEGNO, 4000);
        add(Material.BAMBOO_SIGN, "Cartello Bambu", ShopItem.Category.LEGNO, 6700);
        add(Material.BAMBOO_RAFT, "Zattera Bambu", ShopItem.Category.LEGNO, 16000, false, 0, 1);

        // ============================================================
        // DECORAZIONI - Colori
        // ============================================================
        String[] colors = {"WHITE","ORANGE","MAGENTA","LIGHT_BLUE","YELLOW","LIME","PINK","GRAY","LIGHT_GRAY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
        String[] colorNames = {"Bianca","Arancione","Magenta","Azzurra","Gialla","Verde Lime","Rosa","Grigia","Grigio Ch.","Ciano","Viola","Blu","Marrone","Verde","Rossa","Nera"};
        for (int i = 0; i < colors.length; i++) {
            String c = colors[i]; String cn = colorNames[i];
            try { add(Material.valueOf(c+"_WOOL"), "Lana "+cn, ShopItem.Category.DECORAZIONI, 13400); } catch(Exception e){}
            try { add(Material.valueOf(c+"_CARPET"), "Tappeto "+cn, ShopItem.Category.DECORAZIONI, 8000); } catch(Exception e){}
            try { add(Material.valueOf(c+"_CONCRETE"), "Calcestruzzo "+cn, ShopItem.Category.DECORAZIONI, 18700); } catch(Exception e){}
            try { add(Material.valueOf(c+"_CONCRETE_POWDER"), "Polvere Calcest. "+cn, ShopItem.Category.DECORAZIONI, 16000); } catch(Exception e){}
            try { add(Material.valueOf(c+"_TERRACOTTA"), "Terracotta "+cn, ShopItem.Category.DECORAZIONI, 16000); } catch(Exception e){}
            try { add(Material.valueOf(c+"_GLAZED_TERRACOTTA"), "Terracotta Smalt. "+cn, ShopItem.Category.DECORAZIONI, 21400); } catch(Exception e){}
            try { add(Material.valueOf(c+"_STAINED_GLASS"), "Vetro "+cn, ShopItem.Category.DECORAZIONI, 17400); } catch(Exception e){}
            try { add(Material.valueOf(c+"_STAINED_GLASS_PANE"), "Pannello Vetro "+cn, ShopItem.Category.DECORAZIONI, 13400); } catch(Exception e){}
            try { add(Material.valueOf(c+"_BED"), "Letto "+cn, ShopItem.Category.DECORAZIONI, 24000); } catch(Exception e){}
            try { add(Material.valueOf(c+"_BANNER"), "Stendardo "+cn, ShopItem.Category.DECORAZIONI, 18700); } catch(Exception e){}
            try { add(Material.valueOf(c+"_DYE"), "Colorante "+cn, ShopItem.Category.DECORAZIONI, 2700); } catch(Exception e){}
            try { add(Material.valueOf(c+"_CANDLE"), "Candela "+cn, ShopItem.Category.DECORAZIONI, 10700); } catch(Exception e){}
            try { add(Material.valueOf(c+"_SHULKER_BOX"), "Shulker Box "+cn, ShopItem.Category.DECORAZIONI, 25_000_000, false, 0, 1); } catch(Exception e){}
        }
        add(Material.TERRACOTTA, "Terracotta", ShopItem.Category.DECORAZIONI, 16000);
        add(Material.GLASS, "Vetro", ShopItem.Category.DECORAZIONI, 8000);
        add(Material.GLASS_PANE, "Pannello Vetro", ShopItem.Category.DECORAZIONI, 5300);
        add(Material.TINTED_GLASS, "Vetro Tinto", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.CANDLE, "Candela", ShopItem.Category.DECORAZIONI, 10700);
        add(Material.SHULKER_BOX, "Shulker Box", ShopItem.Category.DECORAZIONI, 25_000_000, false, 0, 1);
        add(Material.FLOWER_POT, "Vaso", ShopItem.Category.DECORAZIONI, 4000);
        add(Material.ITEM_FRAME, "Cornice", ShopItem.Category.DECORAZIONI, 8000);
        add(Material.GLOW_ITEM_FRAME, "Cornice Luminosa", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.PAINTING, "Dipinto", ShopItem.Category.DECORAZIONI, 6700);
        add(Material.ARMOR_STAND, "Espositore Armatura", ShopItem.Category.DECORAZIONI, 13400);
        add(Material.BOOKSHELF, "Libreria", ShopItem.Category.DECORAZIONI, 24000);
        add(Material.CHISELED_BOOKSHELF, "Libreria Cesellata", ShopItem.Category.DECORAZIONI, 32000);
        add(Material.DECORATED_POT, "Vaso Decorato", ShopItem.Category.DECORAZIONI, 16000);
        add(Material.TORCH, "Torcia", ShopItem.Category.DECORAZIONI, 2700);
        add(Material.SOUL_TORCH, "Torcia Anima", ShopItem.Category.DECORAZIONI, 4000);
        add(Material.LANTERN, "Lanterna", ShopItem.Category.DECORAZIONI, 10700);
        add(Material.SOUL_LANTERN, "Lanterna Anima", ShopItem.Category.DECORAZIONI, 13400);
        add(Material.END_ROD, "Bastone End", ShopItem.Category.DECORAZIONI, 32000);
        add(Material.CAMPFIRE, "Falò", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.SOUL_CAMPFIRE, "Falò Anima", ShopItem.Category.DECORAZIONI, 26700);
        add(Material.CHEST, "Forziere", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.TRAPPED_CHEST, "Forziere Trappola", ShopItem.Category.DECORAZIONI, 26700);
        add(Material.BARREL, "Botte", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.CRAFTING_TABLE, "Tavolo Lavoro", ShopItem.Category.DECORAZIONI, 16000);
        add(Material.FURNACE, "Fornace", ShopItem.Category.DECORAZIONI, 18700);
        add(Material.SMOKER, "Affumicatore", ShopItem.Category.DECORAZIONI, 32000);
        add(Material.BLAST_FURNACE, "Altoforno", ShopItem.Category.DECORAZIONI, 40000);
        add(Material.LOOM, "Telaio", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.CARTOGRAPHY_TABLE, "Tavolo Cartografia", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.FLETCHING_TABLE, "Tavolo Frecce", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.SMITHING_TABLE, "Tavolo Fabbro", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.STONECUTTER, "Tagliapietra", ShopItem.Category.DECORAZIONI, 21400);
        add(Material.GRINDSTONE, "Mola", ShopItem.Category.DECORAZIONI, 40000);
        add(Material.COMPOSTER, "Compostiera", ShopItem.Category.DECORAZIONI, 16000);
        add(Material.LECTERN, "Leggio", ShopItem.Category.DECORAZIONI, 32000);
        add(Material.JUKEBOX, "Jukebox", ShopItem.Category.DECORAZIONI, 160000);
        add(Material.NOTE_BLOCK, "Blocco Note", ShopItem.Category.DECORAZIONI, 18700);
        add(Material.IRON_DOOR, "Porta Ferro", ShopItem.Category.DECORAZIONI, 400000);
        add(Material.IRON_TRAPDOOR, "Botola Ferro", ShopItem.Category.DECORAZIONI, 400000);
        add(Material.OCHRE_FROGLIGHT, "Froglight Ocra", ShopItem.Category.DECORAZIONI, 107000);
        add(Material.VERDANT_FROGLIGHT, "Froglight Verde", ShopItem.Category.DECORAZIONI, 107000);
        add(Material.PEARLESCENT_FROGLIGHT, "Froglight Perlato", ShopItem.Category.DECORAZIONI, 107000);
        add(Material.SHROOMLIGHT, "Shroomlight", ShopItem.Category.DECORAZIONI, 53500);
        add(Material.GLOWSTONE, "Glowstone", ShopItem.Category.DECORAZIONI, 107000);
        add(Material.REDSTONE_LAMP, "Lampada Redstone", ShopItem.Category.DECORAZIONI, 40000);

        // ============================================================
        // MINERALI
        // ============================================================
        add(Material.COAL, "Carbone", ShopItem.Category.MINERALI, 50_000);
        add(Material.COAL_ORE, "Minerale Carbone", ShopItem.Category.MINERALI, 60_000);
        add(Material.DEEPSLATE_COAL_ORE, "Min. Carbone Deep.", ShopItem.Category.MINERALI, 65_000);
        add(Material.COAL_BLOCK, "Blocco Carbone", ShopItem.Category.MINERALI, 400_000);
        add(Material.IRON_INGOT, "Lingotto Ferro", ShopItem.Category.MINERALI, 200_000);
        add(Material.RAW_IRON, "Ferro Grezzo", ShopItem.Category.MINERALI, 150_000);
        add(Material.IRON_ORE, "Minerale Ferro", ShopItem.Category.MINERALI, 240_000);
        add(Material.DEEPSLATE_IRON_ORE, "Min. Ferro Deep.", ShopItem.Category.MINERALI, 260_000);
        add(Material.IRON_NUGGET, "Pepita Ferro", ShopItem.Category.MINERALI, 22_000);
        add(Material.IRON_BLOCK, "Blocco Ferro", ShopItem.Category.MINERALI, 1_800_000);
        add(Material.GOLD_INGOT, "Lingotto Oro", ShopItem.Category.MINERALI, 400_000);
        add(Material.RAW_GOLD, "Oro Grezzo", ShopItem.Category.MINERALI, 300_000);
        add(Material.GOLD_ORE, "Minerale Oro", ShopItem.Category.MINERALI, 480_000);
        add(Material.DEEPSLATE_GOLD_ORE, "Min. Oro Deep.", ShopItem.Category.MINERALI, 520_000);
        add(Material.GOLD_NUGGET, "Pepita Oro", ShopItem.Category.MINERALI, 44_500);
        add(Material.GOLD_BLOCK, "Blocco Oro", ShopItem.Category.MINERALI, 3_500_000);
        add(Material.DIAMOND, "Diamante", ShopItem.Category.MINERALI, 1_250_000);
        add(Material.DIAMOND_ORE, "Minerale Diamante", ShopItem.Category.MINERALI, 1_500_000);
        add(Material.DEEPSLATE_DIAMOND_ORE, "Min. Diamante Deep.", ShopItem.Category.MINERALI, 1_650_000);
        add(Material.DIAMOND_BLOCK, "Blocco Diamante", ShopItem.Category.MINERALI, 11_000_000);
        add(Material.EMERALD, "Smeraldo", ShopItem.Category.MINERALI, 900_000);
        add(Material.EMERALD_ORE, "Minerale Smeraldo", ShopItem.Category.MINERALI, 1_080_000);
        add(Material.DEEPSLATE_EMERALD_ORE, "Min. Smeraldo Deep.", ShopItem.Category.MINERALI, 1_170_000);
        add(Material.EMERALD_BLOCK, "Blocco Smeraldo", ShopItem.Category.MINERALI, 8_000_000);
        add(Material.LAPIS_LAZULI, "Lapislazzuli", ShopItem.Category.MINERALI, 100_000);
        add(Material.LAPIS_ORE, "Minerale Lapislazzuli", ShopItem.Category.MINERALI, 120_000);
        add(Material.DEEPSLATE_LAPIS_ORE, "Min. Lapis Deep.", ShopItem.Category.MINERALI, 130_000);
        add(Material.LAPIS_BLOCK, "Blocco Lapislazzuli", ShopItem.Category.MINERALI, 900_000);
        add(Material.REDSTONE, "Redstone", ShopItem.Category.MINERALI, 100_000);
        add(Material.REDSTONE_ORE, "Minerale Redstone", ShopItem.Category.MINERALI, 120_000);
        add(Material.DEEPSLATE_REDSTONE_ORE, "Min. Redstone Deep.", ShopItem.Category.MINERALI, 130_000);
        add(Material.REDSTONE_BLOCK, "Blocco Redstone", ShopItem.Category.MINERALI, 800_000);
        add(Material.QUARTZ, "Quarzo", ShopItem.Category.MINERALI, 200_000);
        add(Material.NETHER_QUARTZ_ORE, "Minerale Quarzo", ShopItem.Category.MINERALI, 240_000);
        add(Material.QUARTZ_BLOCK, "Blocco Quarzo", ShopItem.Category.MINERALI, 800_000);
        add(Material.QUARTZ_BRICKS, "Mattoni Quarzo", ShopItem.Category.MINERALI, 900_000);
        add(Material.QUARTZ_PILLAR, "Pilastro Quarzo", ShopItem.Category.MINERALI, 900_000);
        add(Material.CHISELED_QUARTZ_BLOCK, "Quarzo Cesellato", ShopItem.Category.MINERALI, 900_000);
        add(Material.SMOOTH_QUARTZ, "Quarzo Liscio", ShopItem.Category.MINERALI, 850_000);
        add(Material.QUARTZ_STAIRS, "Scale Quarzo", ShopItem.Category.MINERALI, 830_000);
        add(Material.QUARTZ_SLAB, "Lastra Quarzo", ShopItem.Category.MINERALI, 780_000);
        add(Material.SMOOTH_QUARTZ_STAIRS, "Scale Quarzo Liscio", ShopItem.Category.MINERALI, 880_000);
        add(Material.SMOOTH_QUARTZ_SLAB, "Lastra Quarzo Liscio", ShopItem.Category.MINERALI, 830_000);
        add(Material.COPPER_INGOT, "Rame", ShopItem.Category.MINERALI, 150_000);
        add(Material.RAW_COPPER, "Rame Grezzo", ShopItem.Category.MINERALI, 120_000);
        add(Material.COPPER_ORE, "Minerale Rame", ShopItem.Category.MINERALI, 180_000);
        add(Material.DEEPSLATE_COPPER_ORE, "Min. Rame Deep.", ShopItem.Category.MINERALI, 195_000);
        add(Material.COPPER_BLOCK, "Blocco Rame", ShopItem.Category.MINERALI, 1_200_000);
        add(Material.AMETHYST_SHARD, "Ametista", ShopItem.Category.MINERALI, 200_000);
        add(Material.AMETHYST_BLOCK, "Blocco Ametista", ShopItem.Category.MINERALI, 1_500_000);
        add(Material.NETHER_GOLD_ORE, "Minerale Oro Nether", ShopItem.Category.MINERALI, 480_000);

        // ============================================================
        // NATURA
        // ============================================================
        add(Material.SEAGRASS, "Alghe", ShopItem.Category.NATURA, 5300);
        add(Material.KELP, "Alga", ShopItem.Category.NATURA, 4000);
        add(Material.SUGAR_CANE, "Canna Zucchero", ShopItem.Category.NATURA, 8000);
        add(Material.CACTUS, "Cactus", ShopItem.Category.NATURA, 10700);
        add(Material.PUMPKIN, "Zucca", ShopItem.Category.NATURA, 13400);
        add(Material.MELON_SLICE, "Fetta Anguria", ShopItem.Category.NATURA, 800);
        add(Material.VINE, "Rampicante", ShopItem.Category.NATURA, 8000);
        add(Material.LILY_PAD, "Ninfea", ShopItem.Category.NATURA, 13400);
        add(Material.DANDELION, "Dente di Leone", ShopItem.Category.NATURA, 5300);
        add(Material.POPPY, "Papavero", ShopItem.Category.NATURA, 5300);
        add(Material.BLUE_ORCHID, "Orchidea Blu", ShopItem.Category.NATURA, 10700);
        add(Material.ALLIUM, "Allium", ShopItem.Category.NATURA, 10700);
        add(Material.AZURE_BLUET, "Bucaneve", ShopItem.Category.NATURA, 8000);
        add(Material.RED_TULIP, "Tulipano Rosso", ShopItem.Category.NATURA, 8000);
        add(Material.ORANGE_TULIP, "Tulipano Arancione", ShopItem.Category.NATURA, 8000);
        add(Material.WHITE_TULIP, "Tulipano Bianco", ShopItem.Category.NATURA, 8000);
        add(Material.PINK_TULIP, "Tulipano Rosa", ShopItem.Category.NATURA, 8000);
        add(Material.OXEYE_DAISY, "Margherita", ShopItem.Category.NATURA, 8000);
        add(Material.CORNFLOWER, "Fiordaliso", ShopItem.Category.NATURA, 8000);
        add(Material.LILY_OF_THE_VALLEY, "Mughetto", ShopItem.Category.NATURA, 10700);
        add(Material.WITHER_ROSE, "Rosa Wither", ShopItem.Category.NATURA, 107000);
        add(Material.SUNFLOWER, "Girasole", ShopItem.Category.NATURA, 13400);
        add(Material.LILAC, "Lilla", ShopItem.Category.NATURA, 13400);
        add(Material.ROSE_BUSH, "Rosa", ShopItem.Category.NATURA, 13400);
        add(Material.PEONY, "Peonia", ShopItem.Category.NATURA, 13400);
        add(Material.RED_MUSHROOM, "Fungo Rosso", ShopItem.Category.NATURA, 8000);
        add(Material.BROWN_MUSHROOM, "Fungo Marrone", ShopItem.Category.NATURA, 8000);
        add(Material.STRING, "Filo", ShopItem.Category.NATURA, 80_000);
        add(Material.FEATHER, "Piuma", ShopItem.Category.NATURA, 50_000);
        add(Material.LEATHER, "Cuoio", ShopItem.Category.NATURA, 120_000);
        add(Material.INK_SAC, "Inchiostro", ShopItem.Category.NATURA, 213_000);
        add(Material.GLOW_INK_SAC, "Inchiostro Luminoso", ShopItem.Category.NATURA, 427_000);
        add(Material.SLIME_BALL, "Muco", ShopItem.Category.NATURA, 300_000);
        add(Material.GUNPOWDER, "Polvere da Sparo", ShopItem.Category.NATURA, 200_000);
        add(Material.BONE, "Osso", ShopItem.Category.NATURA, 100_000);
        add(Material.BONE_MEAL, "Farina d'Osso", ShopItem.Category.NATURA, 120_000);
        add(Material.BONE_BLOCK, "Blocco Ossa", ShopItem.Category.NATURA, 1_080_000);
        add(Material.FLINT, "Selce", ShopItem.Category.NATURA, 8000);
        add(Material.HONEYCOMB, "Favo", ShopItem.Category.NATURA, 400_000);
        add(Material.HONEYCOMB_BLOCK, "Blocco Favo", ShopItem.Category.NATURA, 1_600_000);
        add(Material.HONEY_BLOCK, "Blocco Miele", ShopItem.Category.NATURA, 1_280_000);
        add(Material.EGG, "Uovo", ShopItem.Category.NATURA, 50_000, false, 0, 16);
        add(Material.COD, "Merluzzo", ShopItem.Category.NATURA, 8000);
        add(Material.SALMON, "Salmone", ShopItem.Category.NATURA, 10700);
        add(Material.TROPICAL_FISH, "Pesce Tropicale", ShopItem.Category.NATURA, 13400);
        add(Material.PUFFERFISH, "Pesce Palla", ShopItem.Category.NATURA, 16000);
        add(Material.PHANTOM_MEMBRANE, "Membrana Phantom", ShopItem.Category.NATURA, 800_000);
        add(Material.TURTLE_SCUTE, "Scudo Tartaruga", ShopItem.Category.NATURA, 1_340_000);
        add(Material.NAUTILUS_SHELL, "Conchiglia Nautilo", ShopItem.Category.NATURA, 800_000);
        add(Material.PRISMARINE_SHARD, "Frammento Prismarina", ShopItem.Category.NATURA, 13400);
        add(Material.PRISMARINE_CRYSTALS, "Cristalli Prismarina", ShopItem.Category.NATURA, 21400);
        add(Material.SPIDER_EYE, "Occhio Ragno", ShopItem.Category.NATURA, 240_000);
        add(Material.FERMENTED_SPIDER_EYE, "Occhio Fermentato", ShopItem.Category.NATURA, 427_000);
        add(Material.RABBIT_HIDE, "Pelle Coniglio", ShopItem.Category.NATURA, 160_000);
        add(Material.RABBIT_FOOT, "Zampa Coniglio", ShopItem.Category.NATURA, 480_000);
        add(Material.WHEAT, "Grano", ShopItem.Category.NATURA, 100_000);
        add(Material.WHEAT_SEEDS, "Semi Grano", ShopItem.Category.NATURA, 26700);
        add(Material.POTATO, "Patata", ShopItem.Category.NATURA, 90_000);
        add(Material.CARROT, "Carota", ShopItem.Category.NATURA, 90_000);
        add(Material.BEETROOT, "Barbabietola", ShopItem.Category.NATURA, 40000);
        add(Material.BEETROOT_SEEDS, "Semi Barbabietola", ShopItem.Category.NATURA, 26700);
        add(Material.PUMPKIN_SEEDS, "Semi Zucca", ShopItem.Category.NATURA, 40000);
        add(Material.MELON_SEEDS, "Semi Anguria", ShopItem.Category.NATURA, 40000);
        add(Material.APPLE, "Mela", ShopItem.Category.NATURA, 120_000);
        add(Material.SWEET_BERRIES, "Bacche Dolci", ShopItem.Category.NATURA, 80_000);
        add(Material.GLOW_BERRIES, "Bacche Luminose", ShopItem.Category.NATURA, 107_000);
        add(Material.COCOA_BEANS, "Cacao", ShopItem.Category.NATURA, 133_000);
        add(Material.SPORE_BLOSSOM, "Fiore Spora", ShopItem.Category.NATURA, 320_000);
        add(Material.AZALEA, "Azalea", ShopItem.Category.NATURA, 160_000);
        add(Material.FLOWERING_AZALEA, "Azalea in Fiore", ShopItem.Category.NATURA, 240_000);
        add(Material.MOSS_BLOCK, "Blocco Muschio", ShopItem.Category.NATURA, 133_000);
        add(Material.MOSS_CARPET, "Tappeto Muschio", ShopItem.Category.NATURA, 53_000);
        add(Material.BREEZE_ROD, "Bastone Brezza", ShopItem.Category.NATURA, 2_140_000);
        add(Material.ARMADILLO_SCUTE, "Scudo Armadillo", ShopItem.Category.NATURA, 1_340_000);
        add(Material.TORCHFLOWER_SEEDS, "Semi Torcia Fiore", ShopItem.Category.NATURA, 133_000);
        add(Material.PITCHER_POD, "Baccello Brocca", ShopItem.Category.NATURA, 160_000);
        add(Material.PINK_PETALS, "Petali Rosa", ShopItem.Category.NATURA, 107_000);
        add(Material.DEAD_BUSH, "Cespuglio Secco", ShopItem.Category.NATURA, 2700);
        add(Material.FERN, "Felce", ShopItem.Category.NATURA, 2700);
        add(Material.LARGE_FERN, "Felce Grande", ShopItem.Category.NATURA, 4000);
        add(Material.TALL_GRASS, "Erba Alta", ShopItem.Category.NATURA, 2700);
        add(Material.PAPER, "Carta", ShopItem.Category.NATURA, 5300);
        add(Material.BOOK, "Libro", ShopItem.Category.NATURA, 16000);
        add(Material.GOAT_HORN, "Corno di Capra", ShopItem.Category.NATURA, 800_000, false, 0, 1);
        add(Material.BUCKET, "Secchio", ShopItem.Category.NATURA, 533_000, false, 0, 16);
        add(Material.WATER_BUCKET, "Secchio Acqua", ShopItem.Category.NATURA, 640_000, false, 0, 16);
        add(Material.LAVA_BUCKET, "Secchio Lava", ShopItem.Category.NATURA, 853_000, false, 0, 16);
        add(Material.MILK_BUCKET, "Secchio Latte", ShopItem.Category.NATURA, 400_000, false, 0, 16);
        add(Material.SADDLE, "Sella", ShopItem.Category.NATURA, 13_400_000, false, 0, 1);
        add(Material.LEAD, "Guinzaglio", ShopItem.Category.NATURA, 240_000);
        add(Material.NAME_TAG, "Targhetta", ShopItem.Category.NATURA, 800_000);
        add(Material.BRUSH, "Pennello", ShopItem.Category.NATURA, 267_000, false, 0, 1);
        add(Material.SPYGLASS, "Cannocchiale", ShopItem.Category.NATURA, 800_000, false, 0, 1);

        // ============================================================
        // NETHER
        // ============================================================
        add(Material.NETHERRACK, "Netherrack", ShopItem.Category.NETHER, 100_000);
        add(Material.NETHER_BRICK, "Mattone Nether", ShopItem.Category.NETHER, 13400);
        add(Material.NETHER_BRICKS, "Mattoni Nether", ShopItem.Category.NETHER, 400_000);
        add(Material.RED_NETHER_BRICKS, "Mattoni Nether Rossi", ShopItem.Category.NETHER, 480_000);
        add(Material.CRACKED_NETHER_BRICKS, "Mattoni Nether Inc.", ShopItem.Category.NETHER, 440_000);
        add(Material.CHISELED_NETHER_BRICKS, "Mattoni Nether Ces.", ShopItem.Category.NETHER, 480_000);
        add(Material.NETHER_BRICK_STAIRS, "Scale Nether", ShopItem.Category.NETHER, 430_000);
        add(Material.NETHER_BRICK_SLAB, "Lastra Nether", ShopItem.Category.NETHER, 380_000);
        add(Material.NETHER_BRICK_FENCE, "Staccionata Nether", ShopItem.Category.NETHER, 420_000);
        add(Material.NETHER_BRICK_WALL, "Muro Nether", ShopItem.Category.NETHER, 420_000);
        add(Material.RED_NETHER_BRICK_STAIRS, "Scale Nether Rosso", ShopItem.Category.NETHER, 510_000);
        add(Material.RED_NETHER_BRICK_SLAB, "Lastra Nether Rosso", ShopItem.Category.NETHER, 460_000);
        add(Material.RED_NETHER_BRICK_WALL, "Muro Nether Rosso", ShopItem.Category.NETHER, 500_000);
        add(Material.NETHER_WART, "Verruca Nether", ShopItem.Category.NETHER, 400_000);
        add(Material.NETHER_WART_BLOCK, "Blocco Verruca", ShopItem.Category.NETHER, 320_000);
        add(Material.BLAZE_ROD, "Bastone Blaze", ShopItem.Category.NETHER, 800_000);
        add(Material.BLAZE_POWDER, "Polvere Blaze", ShopItem.Category.NETHER, 600_000);
        add(Material.GHAST_TEAR, "Lacrima Ghast", ShopItem.Category.NETHER, 2_500_000);
        add(Material.MAGMA_CREAM, "Crema Magma", ShopItem.Category.NETHER, 700_000);
        add(Material.SOUL_SAND, "Sabbia Anima", ShopItem.Category.NETHER, 200_000);
        add(Material.SOUL_SOIL, "Terra Anima", ShopItem.Category.NETHER, 160_000);
        add(Material.GLOWSTONE_DUST, "Polvere Glowstone", ShopItem.Category.NETHER, 300_000);
        add(Material.WITHER_SKELETON_SKULL, "Teschio Wither", ShopItem.Category.NETHER, 5_340_000);
        add(Material.CRIMSON_STEM, "Fungo Cremisi", ShopItem.Category.NETHER, 13400);
        add(Material.WARPED_STEM, "Fungo Deformato", ShopItem.Category.NETHER, 13400);
        add(Material.CRIMSON_PLANKS, "Assi Cremisi", ShopItem.Category.NETHER, 10700);
        add(Material.WARPED_PLANKS, "Assi Deformate", ShopItem.Category.NETHER, 10700);
        add(Material.CRIMSON_STAIRS, "Scale Cremisi", ShopItem.Category.NETHER, 11500);
        add(Material.WARPED_STAIRS, "Scale Deformate", ShopItem.Category.NETHER, 11500);
        add(Material.CRIMSON_SLAB, "Lastra Cremisi", ShopItem.Category.NETHER, 9600);
        add(Material.WARPED_SLAB, "Lastra Deformata", ShopItem.Category.NETHER, 9600);
        add(Material.CRIMSON_FENCE, "Staccionata Cremisi", ShopItem.Category.NETHER, 12500);
        add(Material.WARPED_FENCE, "Staccionata Deformata", ShopItem.Category.NETHER, 12500);
        add(Material.CRIMSON_DOOR, "Porta Cremisi", ShopItem.Category.NETHER, 15000);
        add(Material.WARPED_DOOR, "Porta Deformata", ShopItem.Category.NETHER, 15000);
        add(Material.CRIMSON_TRAPDOOR, "Botola Cremisi", ShopItem.Category.NETHER, 13500);
        add(Material.WARPED_TRAPDOOR, "Botola Deformata", ShopItem.Category.NETHER, 13500);
        add(Material.CRIMSON_NYLIUM, "Nylium Cremisi", ShopItem.Category.NETHER, 13400);
        add(Material.WARPED_NYLIUM, "Nylium Deformato", ShopItem.Category.NETHER, 13400);
        add(Material.MAGMA_BLOCK, "Blocco Magma", ShopItem.Category.NETHER, 24000);
        add(Material.WEEPING_VINES, "Viti Piangenti", ShopItem.Category.NETHER, 8000);
        add(Material.TWISTING_VINES, "Viti Attorcigliate", ShopItem.Category.NETHER, 8000);
        add(Material.CRIMSON_ROOTS, "Radici Cremisi", ShopItem.Category.NETHER, 5300);
        add(Material.WARPED_ROOTS, "Radici Deformate", ShopItem.Category.NETHER, 5300);
        add(Material.CRIMSON_FUNGUS, "Fungo Cremisi", ShopItem.Category.NETHER, 8000);
        add(Material.WARPED_FUNGUS, "Fungo Deformato", ShopItem.Category.NETHER, 8000);
        add(Material.WARPED_WART_BLOCK, "Blocco Verruca Def.", ShopItem.Category.NETHER, 320_000);
        add(Material.GOLD_NUGGET, "Pepita Oro", ShopItem.Category.NETHER, 44_500);
        add(Material.FIRE_CHARGE, "Palla di Fuoco", ShopItem.Category.NETHER, 16000);

        // ============================================================
        // END
        // ============================================================
        add(Material.END_STONE, "Pietra End", ShopItem.Category.END, 40000);
        add(Material.END_STONE_BRICKS, "Mattoni End", ShopItem.Category.END, 48000);
        add(Material.END_STONE_BRICK_STAIRS, "Scale End", ShopItem.Category.END, 50700);
        add(Material.END_STONE_BRICK_SLAB, "Lastra End", ShopItem.Category.END, 45300);
        add(Material.END_STONE_BRICK_WALL, "Muro End", ShopItem.Category.END, 49300);
        add(Material.PURPUR_BLOCK, "Purpur", ShopItem.Category.END, 53500);
        add(Material.PURPUR_PILLAR, "Pilastro Purpur", ShopItem.Category.END, 58700);
        add(Material.PURPUR_STAIRS, "Scale Purpur", ShopItem.Category.END, 56000);
        add(Material.PURPUR_SLAB, "Lastra Purpur", ShopItem.Category.END, 50700);
        add(Material.ENDER_PEARL, "Perla Ender", ShopItem.Category.END, 500_000, false, 0, 16);
        add(Material.ENDER_EYE, "Occhio Ender", ShopItem.Category.END, 2_670_000);
        add(Material.DRAGON_BREATH, "Respiro Drago", ShopItem.Category.END, 4_000_000);
        add(Material.SHULKER_SHELL, "Guscio Shulker", ShopItem.Category.END, 10_000_000);
        add(Material.CHORUS_FRUIT, "Frutto Coro", ShopItem.Category.END, 53_500);
        add(Material.CHORUS_FLOWER, "Fiore Coro", ShopItem.Category.END, 107_000);
        add(Material.POPPED_CHORUS_FRUIT, "Frutto Coro Esploso", ShopItem.Category.END, 66_700);
        add(Material.END_ROD, "Bastone End", ShopItem.Category.END, 320_000);
        add(Material.END_CRYSTAL, "Cristallo End", ShopItem.Category.END, 50_000_000);
        add(Material.ECHO_SHARD, "Frammento Eco", ShopItem.Category.END, 66_700_000);
        add(Material.ENDER_CHEST, "Forziere Ender", ShopItem.Category.END, 53_500_000);
        add(Material.SCULK, "Sculk", ShopItem.Category.END, 32000);
        add(Material.SCULK_SENSOR, "Sensore Sculk", ShopItem.Category.END, 160_000);
        add(Material.CALIBRATED_SCULK_SENSOR, "Sensore Sculk Cal.", ShopItem.Category.END, 320_000);
        add(Material.SCULK_CATALYST, "Catalizzatore Sculk", ShopItem.Category.END, 320_000);
        add(Material.SCULK_SHRIEKER, "Strillatore Sculk", ShopItem.Category.END, 480_000);
        add(Material.SCULK_VEIN, "Vena Sculk", ShopItem.Category.END, 21400);

        // ============================================================
        // REDSTONE
        // ============================================================
        add(Material.REDSTONE, "Redstone", ShopItem.Category.REDSTONE, 100_000);
        add(Material.REDSTONE_BLOCK, "Blocco Redstone", ShopItem.Category.REDSTONE, 800_000);
        add(Material.REDSTONE_TORCH, "Torcia Redstone", ShopItem.Category.REDSTONE, 8000);
        add(Material.REDSTONE_LAMP, "Lampada Redstone", ShopItem.Category.REDSTONE, 40000);
        add(Material.REPEATER, "Ripetitore", ShopItem.Category.REDSTONE, 300_000);
        add(Material.COMPARATOR, "Comparatore", ShopItem.Category.REDSTONE, 240_000);
        add(Material.OBSERVER, "Osservatore", ShopItem.Category.REDSTONE, 320_000);
        add(Material.PISTON, "Pistone", ShopItem.Category.REDSTONE, 320_000);
        add(Material.STICKY_PISTON, "Pistone Appiccicoso", ShopItem.Category.REDSTONE, 480_000);
        add(Material.SLIME_BLOCK, "Blocco Muco", ShopItem.Category.REDSTONE, 533_000);
        add(Material.HONEY_BLOCK, "Blocco Miele", ShopItem.Category.REDSTONE, 800_000);
        add(Material.DISPENSER, "Distributore", ShopItem.Category.REDSTONE, 240_000);
        add(Material.DROPPER, "Espulsore", ShopItem.Category.REDSTONE, 160_000);
        add(Material.HOPPER, "Tramoggia", ShopItem.Category.REDSTONE, 1_500_000);
        add(Material.LEVER, "Leva", ShopItem.Category.REDSTONE, 5300);
        add(Material.STONE_BUTTON, "Pulsante Pietra", ShopItem.Category.REDSTONE, 5300);
        add(Material.STONE_PRESSURE_PLATE, "Piattaforma Pietra", ShopItem.Category.REDSTONE, 8000);
        add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "Piattaforma Ferro", ShopItem.Category.REDSTONE, 133_000);
        add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, "Piattaforma Oro", ShopItem.Category.REDSTONE, 213_000);
        add(Material.TARGET, "Bersaglio", ShopItem.Category.REDSTONE, 16000);
        add(Material.DAYLIGHT_DETECTOR, "Rilevatore Luce", ShopItem.Category.REDSTONE, 24000);
        add(Material.TRIPWIRE_HOOK, "Gancio Filo", ShopItem.Category.REDSTONE, 8000);
        add(Material.TNT, "TNT", ShopItem.Category.REDSTONE, 107_000);
        add(Material.MINECART, "Vagoncino", ShopItem.Category.REDSTONE, 400_000, false, 0, 1);
        add(Material.CHEST_MINECART, "Vagoncino Forziere", ShopItem.Category.REDSTONE, 533_000, false, 0, 1);
        add(Material.HOPPER_MINECART, "Vagoncino Tramoggia", ShopItem.Category.REDSTONE, 667_000, false, 0, 1);
        add(Material.FURNACE_MINECART, "Vagoncino Fornace", ShopItem.Category.REDSTONE, 480_000, false, 0, 1);
        add(Material.RAIL, "Binario", ShopItem.Category.REDSTONE, 8000);
        add(Material.POWERED_RAIL, "Binario Alimentato", ShopItem.Category.REDSTONE, 21400);
        add(Material.DETECTOR_RAIL, "Binario Rilevatore", ShopItem.Category.REDSTONE, 16000);
        add(Material.ACTIVATOR_RAIL, "Binario Attivatore", ShopItem.Category.REDSTONE, 16000);
        add(Material.COMPASS, "Bussola", ShopItem.Category.REDSTONE, 32000);
        add(Material.CLOCK, "Orologio", ShopItem.Category.REDSTONE, 32000);
        add(Material.FIREWORK_ROCKET, "Razzo", ShopItem.Category.REDSTONE, 8000);
        add(Material.FIREWORK_STAR, "Stella Razzo", ShopItem.Category.REDSTONE, 13400);
        add(Material.RESPAWN_ANCHOR, "Ancora Respawn", ShopItem.Category.REDSTONE, 4_000_000);
        add(Material.LODESTONE, "Lodestone", ShopItem.Category.REDSTONE, 2_140_000);
        add(Material.LIGHTNING_ROD, "Parafulmine", ShopItem.Category.REDSTONE, 1_340_000);

        // ============================================================
        // RARI
        // ============================================================
        add(Material.TOTEM_OF_UNDYING, "Totem", ShopItem.Category.RARI, 500_000_000, false, 0, 1);
        add(Material.ELYTRA, "Elitra", ShopItem.Category.RARI, 1_000_000_000, false, 0, 1);
        add(Material.HEART_OF_THE_SEA, "Cuore del Mare", ShopItem.Category.RARI, 200_000_000, false, 0, 1);
        add(Material.NETHER_STAR, "Stella Nether", ShopItem.Category.RARI, 300_000_000, false, 0, 1);
        add(Material.TRIDENT, "Tridente", ShopItem.Category.RARI, 150_000_000, false, 0, 1);
        add(Material.END_CRYSTAL, "Cristallo End", ShopItem.Category.RARI, 50_000_000, false, 0, 1);
        add(Material.RECOVERY_COMPASS, "Bussola Recupero", ShopItem.Category.RARI, 40_000_000, false, 0, 1);
    }

    public List<ShopItem> getItemsByCategory(ShopItem.Category category) {
        List<ShopItem> result = new ArrayList<>();
        for (ShopItem item : items) {
            if (item.getCategory() == category && item.isBuyable()) result.add(item);
        }
        return result;
    }

    public ShopItem getShopItem(Material material) {
        for (ShopItem item : items) {
            if (item.getMaterial() == material) return item;
        }
        return null;
    }

    public List<ShopItem> getAllItems() { return items; }

    public boolean hasCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return false;
        Map<Material, Long> pc = cooldowns.get(uuid);
        if (!pc.containsKey(material)) return false;
        ShopItem item = getShopItem(material);
        if (item == null) return false;
        long ms = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return System.currentTimeMillis() - pc.get(material) < ms;
    }

    public long getRemainingCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return 0;
        Map<Material, Long> pc = cooldowns.get(uuid);
        if (!pc.containsKey(material)) return 0;
        ShopItem item = getShopItem(material);
        if (item == null) return 0;
        long ms = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return Math.max(0, ms - (System.currentTimeMillis() - pc.get(material)));
    }

    public void setCooldown(UUID uuid, Material material) {
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(material, System.currentTimeMillis());
    }

    public double getSkipCooldownCost(Material material) {
        ShopItem item = getShopItem(material);
        return item != null ? item.getCurrentPrice() * 2 : 0;
    }

    public void skipCooldown(UUID uuid, Material material) {
        if (cooldowns.containsKey(uuid)) cooldowns.get(uuid).remove(material);
    }

    public void resetAllPrices() {
        for (ShopItem item : items) item.resetPrices();
        plugin.getLogger().info("Prezzi shop resettati!");
        savePrices();
    }

    public void savePrices() {
        File file = new File(plugin.getDataFolder(), "prices.yml");
        YamlConfiguration config = new YamlConfiguration();
        for (ShopItem item : items) {
            config.set("prices." + item.getMaterial().name() + ".buy", item.getCurrentPrice());
            config.set("prices." + item.getMaterial().name() + ".sell", item.getCurrentSellPrice());
        }
        try { config.save(file); } catch (Exception e) { e.printStackTrace(); }
    }

    public void loadPrices() {
        File file = new File(plugin.getDataFolder(), "prices.yml");
        if (!file.exists()) return;
    }
}
