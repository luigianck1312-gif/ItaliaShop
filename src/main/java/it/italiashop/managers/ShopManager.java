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

    // D = prezzo ACQUISTO diamante. Vendita = D*0.6 = 1,250,000
    // Quindi D = 1,250,000 / 0.6 = 2,083,333
    private static final double D = 2_083_333;

    public ShopManager(ItaliaShop plugin) {
        this.plugin = plugin;
        registerItems();
        loadPrices();
        long threeDays = 20L * 60 * 60 * 24 * 3;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::resetAllPrices, threeDays, threeDays);
    }

    // Prezzo vendita = 60% acquisto automaticamente
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
        // BLOCCHI BASE
        // ============================================================
        add(Material.DIRT, "Terra", ShopItem.Category.BLOCCHI, D*0.002);
        add(Material.GRASS_BLOCK, "Erba", ShopItem.Category.BLOCCHI, D*0.0025);
        add(Material.COARSE_DIRT, "Terra Grezza", ShopItem.Category.BLOCCHI, D*0.002);
        add(Material.PODZOL, "Podzol", ShopItem.Category.BLOCCHI, D*0.003);
        add(Material.ROOTED_DIRT, "Terra Radicata", ShopItem.Category.BLOCCHI, D*0.0025);
        add(Material.MUD, "Fango", ShopItem.Category.BLOCCHI, D*0.002);
        add(Material.PACKED_MUD, "Fango Compatto", ShopItem.Category.BLOCCHI, D*0.004);
        add(Material.MUD_BRICKS, "Mattoni Fango", ShopItem.Category.BLOCCHI, D*0.005);
        add(Material.MUD_BRICK_STAIRS, "Scale Fango", ShopItem.Category.BLOCCHI, D*0.003);
        add(Material.MUD_BRICK_SLAB, "Lastra Fango", ShopItem.Category.BLOCCHI, D*0.0015);
        add(Material.MUD_BRICK_WALL, "Muro Fango", ShopItem.Category.BLOCCHI, D*0.0025);
        add(Material.SAND, "Sabbia", ShopItem.Category.BLOCCHI, D*0.003);
        add(Material.RED_SAND, "Sabbia Rossa", ShopItem.Category.BLOCCHI, D*0.0032);
        add(Material.GRAVEL, "Ghiaia", ShopItem.Category.BLOCCHI, D*0.0035);
        add(Material.CLAY, "Argilla", ShopItem.Category.BLOCCHI, D*0.006);
        add(Material.CLAY_BALL, "Palla Argilla", ShopItem.Category.BLOCCHI, D*0.0015);
        add(Material.ICE, "Ghiaccio", ShopItem.Category.BLOCCHI, D*0.005);
        add(Material.PACKED_ICE, "Ghiaccio Compatto", ShopItem.Category.BLOCCHI, D*0.01);
        add(Material.BLUE_ICE, "Ghiaccio Blu", ShopItem.Category.BLOCCHI, D*0.02);
        add(Material.SNOW_BLOCK, "Blocco Neve", ShopItem.Category.BLOCCHI, D*0.004);
        add(Material.SNOW, "Neve", ShopItem.Category.BLOCCHI, D*0.001);
        add(Material.OBSIDIAN, "Ossidiana", ShopItem.Category.BLOCCHI, D*0.05);
        add(Material.CRYING_OBSIDIAN, "Ossidiana Piangente", ShopItem.Category.BLOCCHI, D*0.065);
        add(Material.COBWEB, "Ragnatela", ShopItem.Category.BLOCCHI, D*0.008);
        add(Material.SPONGE, "Spugna", ShopItem.Category.BLOCCHI, D*0.5);
        add(Material.DRIPSTONE_BLOCK, "Blocco Dripstone", ShopItem.Category.BLOCCHI, D*0.003);
        add(Material.POINTED_DRIPSTONE, "Stalattite", ShopItem.Category.BLOCCHI, D*0.002);
        add(Material.MYCELIUM, "Micelio", ShopItem.Category.BLOCCHI, D*0.004);

        // ============================================================
        // PIETRA & ROCCIA
        // ============================================================
        add(Material.STONE, "Pietra", ShopItem.Category.PIETRA, D*0.004);
        add(Material.COBBLESTONE, "Ciottoli", ShopItem.Category.PIETRA, D*0.003);
        add(Material.MOSSY_COBBLESTONE, "Ciottoli Muschiati", ShopItem.Category.PIETRA, D*0.004);
        add(Material.SMOOTH_STONE, "Pietra Liscia", ShopItem.Category.PIETRA, D*0.005);
        add(Material.STONE_BRICKS, "Mattoni Pietra", ShopItem.Category.PIETRA, D*0.006);
        add(Material.MOSSY_STONE_BRICKS, "Mattoni Muschiati", ShopItem.Category.PIETRA, D*0.007);
        add(Material.CRACKED_STONE_BRICKS, "Mattoni Incrinati", ShopItem.Category.PIETRA, D*0.0065);
        add(Material.CHISELED_STONE_BRICKS, "Mattoni Cesellati", ShopItem.Category.PIETRA, D*0.007);
        add(Material.STONE_STAIRS, "Scale Pietra", ShopItem.Category.PIETRA, D*0.0024);
        add(Material.STONE_SLAB, "Lastra Pietra", ShopItem.Category.PIETRA, D*0.0012);
        add(Material.STONE_BRICK_STAIRS, "Scale Mattoni Pietra", ShopItem.Category.PIETRA, D*0.0036);
        add(Material.STONE_BRICK_SLAB, "Lastra Mattoni Pietra", ShopItem.Category.PIETRA, D*0.0018);
        add(Material.STONE_BRICK_WALL, "Muro Mattoni Pietra", ShopItem.Category.PIETRA, D*0.003);
        add(Material.COBBLESTONE_STAIRS, "Scale Ciottoli", ShopItem.Category.PIETRA, D*0.0018);
        add(Material.COBBLESTONE_SLAB, "Lastra Ciottoli", ShopItem.Category.PIETRA, D*0.0009);
        add(Material.COBBLESTONE_WALL, "Muro Ciottoli", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.BRICKS, "Mattoni", ShopItem.Category.PIETRA, D*0.012);
        add(Material.BRICK_STAIRS, "Scale Mattoni", ShopItem.Category.PIETRA, D*0.0072);
        add(Material.BRICK_SLAB, "Lastra Mattoni", ShopItem.Category.PIETRA, D*0.0036);
        add(Material.BRICK_WALL, "Muro Mattoni", ShopItem.Category.PIETRA, D*0.006);
        add(Material.SANDSTONE, "Arenaria", ShopItem.Category.PIETRA, D*0.005);
        add(Material.SMOOTH_SANDSTONE, "Arenaria Liscia", ShopItem.Category.PIETRA, D*0.006);
        add(Material.CHISELED_SANDSTONE, "Arenaria Cesellata", ShopItem.Category.PIETRA, D*0.007);
        add(Material.CUT_SANDSTONE, "Arenaria Tagliata", ShopItem.Category.PIETRA, D*0.006);
        add(Material.SANDSTONE_STAIRS, "Scale Arenaria", ShopItem.Category.PIETRA, D*0.003);
        add(Material.SANDSTONE_SLAB, "Lastra Arenaria", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.SANDSTONE_WALL, "Muro Arenaria", ShopItem.Category.PIETRA, D*0.0025);
        add(Material.RED_SANDSTONE, "Arenaria Rossa", ShopItem.Category.PIETRA, D*0.005);
        add(Material.RED_SANDSTONE_STAIRS, "Scale Arenaria Rossa", ShopItem.Category.PIETRA, D*0.003);
        add(Material.RED_SANDSTONE_SLAB, "Lastra Arenaria Rossa", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.RED_SANDSTONE_WALL, "Muro Arenaria Rossa", ShopItem.Category.PIETRA, D*0.0025);
        add(Material.ANDESITE, "Andesite", ShopItem.Category.PIETRA, D*0.003);
        add(Material.POLISHED_ANDESITE, "Andesite Levigata", ShopItem.Category.PIETRA, D*0.004);
        add(Material.ANDESITE_STAIRS, "Scale Andesite", ShopItem.Category.PIETRA, D*0.0018);
        add(Material.ANDESITE_SLAB, "Lastra Andesite", ShopItem.Category.PIETRA, D*0.0009);
        add(Material.ANDESITE_WALL, "Muro Andesite", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.DIORITE, "Diorite", ShopItem.Category.PIETRA, D*0.003);
        add(Material.POLISHED_DIORITE, "Diorite Levigata", ShopItem.Category.PIETRA, D*0.004);
        add(Material.DIORITE_STAIRS, "Scale Diorite", ShopItem.Category.PIETRA, D*0.0018);
        add(Material.DIORITE_SLAB, "Lastra Diorite", ShopItem.Category.PIETRA, D*0.0009);
        add(Material.DIORITE_WALL, "Muro Diorite", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.GRANITE, "Granito", ShopItem.Category.PIETRA, D*0.003);
        add(Material.POLISHED_GRANITE, "Granito Levigato", ShopItem.Category.PIETRA, D*0.004);
        add(Material.GRANITE_STAIRS, "Scale Granito", ShopItem.Category.PIETRA, D*0.0018);
        add(Material.GRANITE_SLAB, "Lastra Granito", ShopItem.Category.PIETRA, D*0.0009);
        add(Material.GRANITE_WALL, "Muro Granito", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.DEEPSLATE, "Deepslate", ShopItem.Category.PIETRA, D*0.004);
        add(Material.COBBLED_DEEPSLATE, "Deepslate Ciottoli", ShopItem.Category.PIETRA, D*0.0035);
        add(Material.POLISHED_DEEPSLATE, "Deepslate Levigata", ShopItem.Category.PIETRA, D*0.005);
        add(Material.DEEPSLATE_BRICKS, "Mattoni Deepslate", ShopItem.Category.PIETRA, D*0.006);
        add(Material.DEEPSLATE_TILES, "Piastrelle Deepslate", ShopItem.Category.PIETRA, D*0.007);
        add(Material.CHISELED_DEEPSLATE, "Deepslate Cesellata", ShopItem.Category.PIETRA, D*0.007);
        add(Material.COBBLED_DEEPSLATE_STAIRS, "Scale Deepslate", ShopItem.Category.PIETRA, D*0.0021);
        add(Material.COBBLED_DEEPSLATE_SLAB, "Lastra Deepslate", ShopItem.Category.PIETRA, D*0.00105);
        add(Material.COBBLED_DEEPSLATE_WALL, "Muro Deepslate", ShopItem.Category.PIETRA, D*0.00175);
        add(Material.TUFF, "Tuff", ShopItem.Category.PIETRA, D*0.003);
        add(Material.POLISHED_TUFF, "Tuff Levigato", ShopItem.Category.PIETRA, D*0.004);
        add(Material.TUFF_BRICKS, "Mattoni Tuff", ShopItem.Category.PIETRA, D*0.005);
        add(Material.TUFF_STAIRS, "Scale Tuff", ShopItem.Category.PIETRA, D*0.003);
        add(Material.TUFF_SLAB, "Lastra Tuff", ShopItem.Category.PIETRA, D*0.0015);
        add(Material.TUFF_WALL, "Muro Tuff", ShopItem.Category.PIETRA, D*0.0025);
        add(Material.BASALT, "Basalto", ShopItem.Category.PIETRA, D*0.003);
        add(Material.SMOOTH_BASALT, "Basalto Liscio", ShopItem.Category.PIETRA, D*0.004);
        add(Material.POLISHED_BASALT, "Basalto Levigato", ShopItem.Category.PIETRA, D*0.004);
        add(Material.CALCITE, "Calcite", ShopItem.Category.PIETRA, D*0.003);
        add(Material.BLACKSTONE, "Blackstone", ShopItem.Category.PIETRA, D*0.004);
        add(Material.GILDED_BLACKSTONE, "Blackstone Dorata", ShopItem.Category.PIETRA, D*0.008);
        add(Material.POLISHED_BLACKSTONE, "Blackstone Levigata", ShopItem.Category.PIETRA, D*0.005);
        add(Material.POLISHED_BLACKSTONE_BRICKS, "Mattoni Blackstone", ShopItem.Category.PIETRA, D*0.006);
        add(Material.BLACKSTONE_STAIRS, "Scale Blackstone", ShopItem.Category.PIETRA, D*0.0024);
        add(Material.BLACKSTONE_SLAB, "Lastra Blackstone", ShopItem.Category.PIETRA, D*0.0012);
        add(Material.BLACKSTONE_WALL, "Muro Blackstone", ShopItem.Category.PIETRA, D*0.002);
        add(Material.PRISMARINE, "Prismarina", ShopItem.Category.PIETRA, D*0.01);
        add(Material.PRISMARINE_BRICKS, "Mattoni Prismarina", ShopItem.Category.PIETRA, D*0.013);
        add(Material.DARK_PRISMARINE, "Prismarina Scura", ShopItem.Category.PIETRA, D*0.016);
        add(Material.SEA_LANTERN, "Lanterna Marina", ShopItem.Category.PIETRA, D*0.02);
        add(Material.PRISMARINE_STAIRS, "Scale Prismarina", ShopItem.Category.PIETRA, D*0.006);
        add(Material.PRISMARINE_SLAB, "Lastra Prismarina", ShopItem.Category.PIETRA, D*0.003);
        add(Material.PRISMARINE_SHARD, "Frammento Prismarina", ShopItem.Category.PIETRA, D*0.003);
        add(Material.PRISMARINE_CRYSTALS, "Cristalli Prismarina", ShopItem.Category.PIETRA, D*0.005);

        // ============================================================
        // LEGNO
        // ============================================================
        String[] woodTypes = {"OAK","BIRCH","SPRUCE","JUNGLE","ACACIA","DARK_OAK","MANGROVE","CHERRY"};
        String[] woodNames = {"Quercia","Betulla","Abete","Giungla","Acacia","Rovere","Mangrovia","Ciliegio"};
        for (int i = 0; i < woodTypes.length; i++) {
            String w = woodTypes[i]; String n = woodNames[i];
            double log = D*0.008;
            double plank = log*0.75;
            try { add(Material.valueOf(w+"_LOG"), "Tronco "+n, ShopItem.Category.LEGNO, log); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_WOOD"), "Legno "+n, ShopItem.Category.LEGNO, log*0.9); } catch(Exception e2){}
            try { add(Material.valueOf("STRIPPED_"+w+"_LOG"), "Tronco Scortecciato "+n, ShopItem.Category.LEGNO, log*0.85); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_PLANKS"), "Assi "+n, ShopItem.Category.LEGNO, plank); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_STAIRS"), "Scale "+n, ShopItem.Category.LEGNO, plank*0.6); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_SLAB"), "Lastra "+n, ShopItem.Category.LEGNO, plank*0.3); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_FENCE"), "Staccionata "+n, ShopItem.Category.LEGNO, plank*0.5); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_FENCE_GATE"), "Cancello "+n, ShopItem.Category.LEGNO, plank*0.8); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_DOOR"), "Porta "+n, ShopItem.Category.LEGNO, plank*0.8); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_TRAPDOOR"), "Botola "+n, ShopItem.Category.LEGNO, plank*0.8); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_PRESSURE_PLATE"), "Piattaforma "+n, ShopItem.Category.LEGNO, plank*0.4); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_BUTTON"), "Pulsante "+n, ShopItem.Category.LEGNO, plank*0.3); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_SIGN"), "Cartello "+n, ShopItem.Category.LEGNO, plank*0.5); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_LEAVES"), "Foglie "+n, ShopItem.Category.LEGNO, D*0.002); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_SAPLING"), "Germoglio "+n, ShopItem.Category.LEGNO, D*0.003); } catch(Exception e2){}
            try { add(Material.valueOf(w+"_BOAT"), "Barca "+n, ShopItem.Category.LEGNO, D*0.015, false, 0, 1); } catch(Exception e2){}
        }
        add(Material.BAMBOO, "Bambu", ShopItem.Category.LEGNO, D*0.003);
        add(Material.BAMBOO_BLOCK, "Blocco Bambu", ShopItem.Category.LEGNO, D*0.008);
        add(Material.BAMBOO_PLANKS, "Assi Bambu", ShopItem.Category.LEGNO, D*0.006);
        add(Material.BAMBOO_MOSAIC, "Mosaico Bambu", ShopItem.Category.LEGNO, D*0.007);
        add(Material.BAMBOO_STAIRS, "Scale Bambu", ShopItem.Category.LEGNO, D*0.0036);
        add(Material.BAMBOO_SLAB, "Lastra Bambu", ShopItem.Category.LEGNO, D*0.0018);
        add(Material.BAMBOO_FENCE, "Staccionata Bambu", ShopItem.Category.LEGNO, D*0.003);
        add(Material.BAMBOO_DOOR, "Porta Bambu", ShopItem.Category.LEGNO, D*0.0048);
        add(Material.BAMBOO_TRAPDOOR, "Botola Bambu", ShopItem.Category.LEGNO, D*0.0048);

        // ============================================================
        // DECORAZIONI - Lana, Vetro, Terracotta, Colori
        // ============================================================
        String[] colors = {"WHITE","ORANGE","MAGENTA","LIGHT_BLUE","YELLOW","LIME","PINK","GRAY","LIGHT_GRAY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
        String[] colorNames = {"Bianca","Arancione","Magenta","Azzurra","Gialla","Verde Lime","Rosa","Grigia","Grigio Chiaro","Ciano","Viola","Blu","Marrone","Verde","Rossa","Nera"};
        for (int i = 0; i < colors.length; i++) {
            String c = colors[i]; String cn = colorNames[i];
            try { add(Material.valueOf(c+"_WOOL"), "Lana "+cn, ShopItem.Category.DECORAZIONI, D*0.005); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_CARPET"), "Tappeto "+cn, ShopItem.Category.DECORAZIONI, D*0.002); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_CONCRETE"), "Calcestruzzo "+cn, ShopItem.Category.DECORAZIONI, D*0.006); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_CONCRETE_POWDER"), "Polvere Calcestruzzo "+cn, ShopItem.Category.DECORAZIONI, D*0.005); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_TERRACOTTA"), "Terracotta "+cn, ShopItem.Category.DECORAZIONI, D*0.007); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_GLAZED_TERRACOTTA"), "Terracotta Smaltata "+cn, ShopItem.Category.DECORAZIONI, D*0.009); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_STAINED_GLASS"), "Vetro "+cn, ShopItem.Category.DECORAZIONI, D*0.007); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_STAINED_GLASS_PANE"), "Pannello Vetro "+cn, ShopItem.Category.DECORAZIONI, D*0.003); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_BED"), "Letto "+cn, ShopItem.Category.DECORAZIONI, D*0.01); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_BANNER"), "Stendardo "+cn, ShopItem.Category.DECORAZIONI, D*0.015); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_DYE"), "Colorante "+cn, ShopItem.Category.DECORAZIONI, D*0.002); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_CANDLE"), "Candela "+cn, ShopItem.Category.DECORAZIONI, D*0.003); } catch(Exception e2){}
            try { add(Material.valueOf(c+"_SHULKER_BOX"), "Shulker Box "+cn, ShopItem.Category.DECORAZIONI, D*20, false, 0, 1); } catch(Exception e2){}
        }
        add(Material.TERRACOTTA, "Terracotta", ShopItem.Category.DECORAZIONI, D*0.007);
        add(Material.GLASS, "Vetro", ShopItem.Category.DECORAZIONI, D*0.007);
        add(Material.GLASS_PANE, "Pannello Vetro", ShopItem.Category.DECORAZIONI, D*0.003);
        add(Material.TINTED_GLASS, "Vetro Tinto", ShopItem.Category.DECORAZIONI, D*0.01);
        add(Material.CANDLE, "Candela", ShopItem.Category.DECORAZIONI, D*0.003);
        add(Material.SHULKER_BOX, "Shulker Box", ShopItem.Category.DECORAZIONI, D*20, false, 0, 1);
        add(Material.FLOWER_POT, "Vaso", ShopItem.Category.DECORAZIONI, D*0.002);
        add(Material.ITEM_FRAME, "Cornice", ShopItem.Category.DECORAZIONI, D*0.003);
        add(Material.GLOW_ITEM_FRAME, "Cornice Luminosa", ShopItem.Category.DECORAZIONI, D*0.006);
        add(Material.PAINTING, "Dipinto", ShopItem.Category.DECORAZIONI, D*0.003);
        add(Material.ARMOR_STAND, "Espositore Armatura", ShopItem.Category.DECORAZIONI, D*0.01);
        add(Material.BOOKSHELF, "Libreria", ShopItem.Category.DECORAZIONI, D*0.012);
        add(Material.CHISELED_BOOKSHELF, "Libreria Cesellata", ShopItem.Category.DECORAZIONI, D*0.018);
        add(Material.DECORATED_POT, "Vaso Decorato", ShopItem.Category.DECORAZIONI, D*0.006);
        add(Material.TORCH, "Torcia", ShopItem.Category.DECORAZIONI, D*0.001);
        add(Material.SOUL_TORCH, "Torcia Anima", ShopItem.Category.DECORAZIONI, D*0.0015);
        add(Material.LANTERN, "Lanterna", ShopItem.Category.DECORAZIONI, D*0.004);
        add(Material.SOUL_LANTERN, "Lanterna Anima", ShopItem.Category.DECORAZIONI, D*0.005);
        add(Material.END_ROD, "Bastone End", ShopItem.Category.DECORAZIONI, D*0.012);
        add(Material.CAMPFIRE, "Falò", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.SOUL_CAMPFIRE, "Falò Anima", ShopItem.Category.DECORAZIONI, D*0.01);
        add(Material.CHEST, "Forziere", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.TRAPPED_CHEST, "Forziere Trappola", ShopItem.Category.DECORAZIONI, D*0.01);
        add(Material.BARREL, "Botte", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.CRAFTING_TABLE, "Tavolo Lavoro", ShopItem.Category.DECORAZIONI, D*0.006);
        add(Material.FURNACE, "Fornace", ShopItem.Category.DECORAZIONI, D*0.007);
        add(Material.SMOKER, "Affumicatore", ShopItem.Category.DECORAZIONI, D*0.012);
        add(Material.BLAST_FURNACE, "Altoforno", ShopItem.Category.DECORAZIONI, D*0.015);
        add(Material.LOOM, "Telaio", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.CARTOGRAPHY_TABLE, "Tavolo Cartografia", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.FLETCHING_TABLE, "Tavolo Frecce", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.SMITHING_TABLE, "Tavolo Fabbro", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.STONECUTTER, "Tagliapietra", ShopItem.Category.DECORAZIONI, D*0.008);
        add(Material.GRINDSTONE, "Mola", ShopItem.Category.DECORAZIONI, D*0.015);
        add(Material.COMPOSTER, "Compostiera", ShopItem.Category.DECORAZIONI, D*0.006);
        add(Material.LECTERN, "Leggio", ShopItem.Category.DECORAZIONI, D*0.012);
        add(Material.JUKEBOX, "Jukebox", ShopItem.Category.DECORAZIONI, D*0.06);
        add(Material.NOTE_BLOCK, "Blocco Note", ShopItem.Category.DECORAZIONI, D*0.007);
        add(Material.IRON_DOOR, "Porta Ferro", ShopItem.Category.DECORAZIONI, D*0.15);
        add(Material.IRON_TRAPDOOR, "Botola Ferro", ShopItem.Category.DECORAZIONI, D*0.15);

        // Ochre/Verdant/Pearlescent Froglight
        add(Material.OCHRE_FROGLIGHT, "Froglight Ocra", ShopItem.Category.DECORAZIONI, D*0.04);
        add(Material.VERDANT_FROGLIGHT, "Froglight Verde", ShopItem.Category.DECORAZIONI, D*0.04);
        add(Material.PEARLESCENT_FROGLIGHT, "Froglight Perlato", ShopItem.Category.DECORAZIONI, D*0.04);
        add(Material.SHROOMLIGHT, "Shroomlight", ShopItem.Category.DECORAZIONI, D*0.02);
        add(Material.GLOWSTONE, "Glowstone", ShopItem.Category.DECORAZIONI, D*0.04);
        add(Material.SEA_LANTERN, "Lanterna Marina", ShopItem.Category.DECORAZIONI, D*0.02);
        add(Material.REDSTONE_LAMP, "Lampada Redstone", ShopItem.Category.DECORAZIONI, D*0.015);

        // ============================================================
        // MINERALI
        // ============================================================
        add(Material.COAL, "Carbone", ShopItem.Category.MINERALI, D*0.008);
        add(Material.COAL_ORE, "Minerale Carbone", ShopItem.Category.MINERALI, D*0.01);
        add(Material.COAL_BLOCK, "Blocco Carbone", ShopItem.Category.MINERALI, D*0.072);
        add(Material.IRON_INGOT, "Lingotto Ferro", ShopItem.Category.MINERALI, D*0.05);
        add(Material.RAW_IRON, "Ferro Grezzo", ShopItem.Category.MINERALI, D*0.045);
        add(Material.IRON_ORE, "Minerale Ferro", ShopItem.Category.MINERALI, D*0.06);
        add(Material.IRON_NUGGET, "Pepita Ferro", ShopItem.Category.MINERALI, D*0.0055);
        add(Material.IRON_BLOCK, "Blocco Ferro", ShopItem.Category.MINERALI, D*0.45);
        add(Material.GOLD_INGOT, "Lingotto Oro", ShopItem.Category.MINERALI, D*0.1);
        add(Material.RAW_GOLD, "Oro Grezzo", ShopItem.Category.MINERALI, D*0.09);
        add(Material.GOLD_ORE, "Minerale Oro", ShopItem.Category.MINERALI, D*0.12);
        add(Material.GOLD_NUGGET, "Pepita Oro", ShopItem.Category.MINERALI, D*0.011);
        add(Material.GOLD_BLOCK, "Blocco Oro", ShopItem.Category.MINERALI, D*0.9);
        add(Material.DIAMOND, "Diamante", ShopItem.Category.MINERALI, D);
        add(Material.DIAMOND_ORE, "Minerale Diamante", ShopItem.Category.MINERALI, D*1.2);
        add(Material.DIAMOND_BLOCK, "Blocco Diamante", ShopItem.Category.MINERALI, D*9);
        add(Material.EMERALD, "Smeraldo", ShopItem.Category.MINERALI, D*0.6);
        add(Material.EMERALD_ORE, "Minerale Smeraldo", ShopItem.Category.MINERALI, D*0.72);
        add(Material.EMERALD_BLOCK, "Blocco Smeraldo", ShopItem.Category.MINERALI, D*5.4);
        add(Material.LAPIS_LAZULI, "Lapislazzuli", ShopItem.Category.MINERALI, D*0.03);
        add(Material.LAPIS_ORE, "Minerale Lapislazzuli", ShopItem.Category.MINERALI, D*0.036);
        add(Material.LAPIS_BLOCK, "Blocco Lapislazzuli", ShopItem.Category.MINERALI, D*0.27);
        add(Material.REDSTONE, "Redstone", ShopItem.Category.MINERALI, D*0.02);
        add(Material.REDSTONE_ORE, "Minerale Redstone", ShopItem.Category.MINERALI, D*0.024);
        add(Material.REDSTONE_BLOCK, "Blocco Redstone", ShopItem.Category.MINERALI, D*0.18);
        add(Material.QUARTZ, "Quarzo", ShopItem.Category.MINERALI, D*0.025);
        add(Material.QUARTZ_BLOCK, "Blocco Quarzo", ShopItem.Category.MINERALI, D*0.1);
        add(Material.QUARTZ_BRICKS, "Mattoni Quarzo", ShopItem.Category.MINERALI, D*0.11);
        add(Material.QUARTZ_PILLAR, "Pilastro Quarzo", ShopItem.Category.MINERALI, D*0.11);
        add(Material.CHISELED_QUARTZ_BLOCK, "Quarzo Cesellato", ShopItem.Category.MINERALI, D*0.11);
        add(Material.SMOOTH_QUARTZ, "Quarzo Liscio", ShopItem.Category.MINERALI, D*0.1);
        add(Material.QUARTZ_STAIRS, "Scale Quarzo", ShopItem.Category.MINERALI, D*0.06);
        add(Material.QUARTZ_SLAB, "Lastra Quarzo", ShopItem.Category.MINERALI, D*0.03);
        add(Material.COPPER_INGOT, "Rame", ShopItem.Category.MINERALI, D*0.015);
        add(Material.RAW_COPPER, "Rame Grezzo", ShopItem.Category.MINERALI, D*0.013);
        add(Material.COPPER_ORE, "Minerale Rame", ShopItem.Category.MINERALI, D*0.018);
        add(Material.COPPER_BLOCK, "Blocco Rame", ShopItem.Category.MINERALI, D*0.135);
        add(Material.AMETHYST_SHARD, "Ametista", ShopItem.Category.MINERALI, D*0.04);
        add(Material.AMETHYST_BLOCK, "Blocco Ametista", ShopItem.Category.MINERALI, D*0.16);
        add(Material.NETHER_QUARTZ_ORE, "Minerale Quarzo Nether", ShopItem.Category.MINERALI, D*0.03);
        add(Material.NETHER_GOLD_ORE, "Minerale Oro Nether", ShopItem.Category.MINERALI, D*0.08);

        // ============================================================
        // NATURA & PIANTE
        // ============================================================
        add(Material.SEAGRASS, "Alghe", ShopItem.Category.NATURA, D*0.002);
        add(Material.KELP, "Alga", ShopItem.Category.NATURA, D*0.0015);
        add(Material.SUGAR_CANE, "Canna Zucchero", ShopItem.Category.NATURA, D*0.003);
        add(Material.CACTUS, "Cactus", ShopItem.Category.NATURA, D*0.004);
        add(Material.PUMPKIN, "Zucca", ShopItem.Category.NATURA, D*0.005);
        add(Material.MELON_SLICE, "Fetta Anguria", ShopItem.Category.NATURA, D*0.0003);
        add(Material.VINE, "Rampicante", ShopItem.Category.NATURA, D*0.003);
        add(Material.LILY_PAD, "Ninfea", ShopItem.Category.NATURA, D*0.005);
        add(Material.DANDELION, "Dente di Leone", ShopItem.Category.NATURA, D*0.002);
        add(Material.POPPY, "Papavero", ShopItem.Category.NATURA, D*0.002);
        add(Material.BLUE_ORCHID, "Orchidea Blu", ShopItem.Category.NATURA, D*0.004);
        add(Material.ALLIUM, "Allium", ShopItem.Category.NATURA, D*0.004);
        add(Material.AZURE_BLUET, "Bucaneve", ShopItem.Category.NATURA, D*0.003);
        add(Material.RED_TULIP, "Tulipano Rosso", ShopItem.Category.NATURA, D*0.003);
        add(Material.ORANGE_TULIP, "Tulipano Arancione", ShopItem.Category.NATURA, D*0.003);
        add(Material.WHITE_TULIP, "Tulipano Bianco", ShopItem.Category.NATURA, D*0.003);
        add(Material.PINK_TULIP, "Tulipano Rosa", ShopItem.Category.NATURA, D*0.003);
        add(Material.OXEYE_DAISY, "Margherita", ShopItem.Category.NATURA, D*0.003);
        add(Material.CORNFLOWER, "Fiordaliso", ShopItem.Category.NATURA, D*0.003);
        add(Material.LILY_OF_THE_VALLEY, "Mughetto", ShopItem.Category.NATURA, D*0.004);
        add(Material.WITHER_ROSE, "Rosa Wither", ShopItem.Category.NATURA, D*0.04);
        add(Material.SUNFLOWER, "Girasole", ShopItem.Category.NATURA, D*0.005);
        add(Material.LILAC, "Lilla", ShopItem.Category.NATURA, D*0.005);
        add(Material.ROSE_BUSH, "Rosa", ShopItem.Category.NATURA, D*0.005);
        add(Material.PEONY, "Peonia", ShopItem.Category.NATURA, D*0.005);
        add(Material.RED_MUSHROOM, "Fungo Rosso", ShopItem.Category.NATURA, D*0.003);
        add(Material.BROWN_MUSHROOM, "Fungo Marrone", ShopItem.Category.NATURA, D*0.003);
        add(Material.STRING, "Filo", ShopItem.Category.NATURA, D*0.006);
        add(Material.FEATHER, "Piuma", ShopItem.Category.NATURA, D*0.005);
        add(Material.LEATHER, "Cuoio", ShopItem.Category.NATURA, D*0.01);
        add(Material.INK_SAC, "Inchiostro", ShopItem.Category.NATURA, D*0.008);
        add(Material.GLOW_INK_SAC, "Inchiostro Luminoso", ShopItem.Category.NATURA, D*0.016);
        add(Material.SLIME_BALL, "Muco", ShopItem.Category.NATURA, D*0.02);
        add(Material.GUNPOWDER, "Polvere da Sparo", ShopItem.Category.NATURA, D*0.012);
        add(Material.BONE, "Osso", ShopItem.Category.NATURA, D*0.006);
        add(Material.BONE_MEAL, "Farina d'Osso", ShopItem.Category.NATURA, D*0.008);
        add(Material.BONE_BLOCK, "Blocco Ossa", ShopItem.Category.NATURA, D*0.072);
        add(Material.FLINT, "Selce", ShopItem.Category.NATURA, D*0.003);
        add(Material.HONEYCOMB, "Favo", ShopItem.Category.NATURA, D*0.009);
        add(Material.HONEYCOMB_BLOCK, "Blocco Favo", ShopItem.Category.NATURA, D*0.036);
        add(Material.HONEY_BLOCK, "Blocco Miele", ShopItem.Category.NATURA, D*0.03);
        add(Material.EGG, "Uovo", ShopItem.Category.NATURA, D*0.002, false, 0, 16);
        add(Material.COD, "Merluzzo", ShopItem.Category.NATURA, D*0.003);
        add(Material.SALMON, "Salmone", ShopItem.Category.NATURA, D*0.004);
        add(Material.TROPICAL_FISH, "Pesce Tropicale", ShopItem.Category.NATURA, D*0.005);
        add(Material.PUFFERFISH, "Pesce Palla", ShopItem.Category.NATURA, D*0.006);
        add(Material.PHANTOM_MEMBRANE, "Membrana Phantom", ShopItem.Category.NATURA, D*0.03);
        add(Material.TURTLE_SCUTE, "Scudo Tartaruga", ShopItem.Category.NATURA, D*0.05);
        add(Material.NAUTILUS_SHELL, "Conchiglia Nautilo", ShopItem.Category.NATURA, D*0.03);
        add(Material.PRISMARINE_SHARD, "Frammento Prismarina", ShopItem.Category.NATURA, D*0.003);
        add(Material.PRISMARINE_CRYSTALS, "Cristalli Prismarina", ShopItem.Category.NATURA, D*0.005);
        add(Material.SPIDER_EYE, "Occhio Ragno", ShopItem.Category.NATURA, D*0.009);
        add(Material.FERMENTED_SPIDER_EYE, "Occhio Fermentato", ShopItem.Category.NATURA, D*0.016);
        add(Material.RABBIT_HIDE, "Pelle Coniglio", ShopItem.Category.NATURA, D*0.006);
        add(Material.RABBIT_FOOT, "Zampa Coniglio", ShopItem.Category.NATURA, D*0.018);
        add(Material.WHEAT, "Grano", ShopItem.Category.NATURA, D*0.003);
        add(Material.WHEAT_SEEDS, "Semi Grano", ShopItem.Category.NATURA, D*0.001);
        add(Material.POTATO, "Patata", ShopItem.Category.NATURA, D*0.002);
        add(Material.CARROT, "Carota", ShopItem.Category.NATURA, D*0.002);
        add(Material.BEETROOT, "Barbabietola", ShopItem.Category.NATURA, D*0.0015);
        add(Material.BEETROOT_SEEDS, "Semi Barbabietola", ShopItem.Category.NATURA, D*0.001);
        add(Material.PUMPKIN_SEEDS, "Semi Zucca", ShopItem.Category.NATURA, D*0.0015);
        add(Material.MELON_SEEDS, "Semi Anguria", ShopItem.Category.NATURA, D*0.0015);
        add(Material.APPLE, "Mela", ShopItem.Category.NATURA, D*0.005);
        add(Material.SWEET_BERRIES, "Bacche Dolci", ShopItem.Category.NATURA, D*0.003);
        add(Material.GLOW_BERRIES, "Bacche Luminose", ShopItem.Category.NATURA, D*0.004);
        add(Material.COCOA_BEANS, "Cacao", ShopItem.Category.NATURA, D*0.005);
        add(Material.SPORE_BLOSSOM, "Fiore Spora", ShopItem.Category.NATURA, D*0.012);
        add(Material.AZALEA, "Azalea", ShopItem.Category.NATURA, D*0.006);
        add(Material.FLOWERING_AZALEA, "Azalea in Fiore", ShopItem.Category.NATURA, D*0.009);
        add(Material.MOSS_BLOCK, "Blocco Muschio", ShopItem.Category.NATURA, D*0.005);
        add(Material.MOSS_CARPET, "Tappeto Muschio", ShopItem.Category.NATURA, D*0.002);
        add(Material.BREEZE_ROD, "Bastone Brezza", ShopItem.Category.NATURA, D*0.08);
        add(Material.ARMADILLO_SCUTE, "Scudo Armadillo", ShopItem.Category.NATURA, D*0.05);
        add(Material.TORCHFLOWER_SEEDS, "Semi Torcia Fiore", ShopItem.Category.NATURA, D*0.005);
        add(Material.PITCHER_POD, "Baccello Brocca", ShopItem.Category.NATURA, D*0.006);
        add(Material.PINK_PETALS, "Petali Rosa", ShopItem.Category.NATURA, D*0.004);
        add(Material.DEAD_BUSH, "Cespuglio Secco", ShopItem.Category.NATURA, D*0.001);
        add(Material.FERN, "Felce", ShopItem.Category.NATURA, D*0.001);
        add(Material.LARGE_FERN, "Felce Grande", ShopItem.Category.NATURA, D*0.0015);
        add(Material.TALL_GRASS, "Erba Alta", ShopItem.Category.NATURA, D*0.001);
        add(Material.PAPER, "Carta", ShopItem.Category.NATURA, D*0.002);
        add(Material.BOOK, "Libro", ShopItem.Category.NATURA, D*0.006);
        add(Material.GOAT_HORN, "Corno di Capra", ShopItem.Category.NATURA, D*0.03, false, 0, 1);
        add(Material.BUCKET, "Secchio", ShopItem.Category.NATURA, D*0.02, false, 0, 16);
        add(Material.WATER_BUCKET, "Secchio Acqua", ShopItem.Category.NATURA, D*0.024, false, 0, 16);
        add(Material.LAVA_BUCKET, "Secchio Lava", ShopItem.Category.NATURA, D*0.032, false, 0, 16);
        add(Material.MILK_BUCKET, "Secchio Latte", ShopItem.Category.NATURA, D*0.015, false, 0, 16);
        add(Material.SADDLE, "Sella", ShopItem.Category.NATURA, D*0.5, false, 0, 1);
        add(Material.LEAD, "Guinzaglio", ShopItem.Category.NATURA, D*0.009);
        add(Material.NAME_TAG, "Targhetta", ShopItem.Category.NATURA, D*0.03);
        add(Material.BRUSH, "Pennello", ShopItem.Category.NATURA, D*0.01, false, 0, 1);
        add(Material.SPYGLASS, "Cannocchiale", ShopItem.Category.NATURA, D*0.03, false, 0, 1);

        // ============================================================
        // NETHER
        // ============================================================
        add(Material.NETHERRACK, "Netherrack", ShopItem.Category.NETHER, D*0.003);
        add(Material.NETHER_BRICKS, "Mattoni Nether", ShopItem.Category.NETHER, D*0.06);
        add(Material.RED_NETHER_BRICKS, "Mattoni Nether Rossi", ShopItem.Category.NETHER, D*0.072);
        add(Material.NETHER_BRICK_STAIRS, "Scale Nether", ShopItem.Category.NETHER, D*0.036);
        add(Material.NETHER_BRICK_SLAB, "Lastra Nether", ShopItem.Category.NETHER, D*0.018);
        add(Material.NETHER_BRICK_FENCE, "Staccionata Nether", ShopItem.Category.NETHER, D*0.03);
        add(Material.NETHER_BRICK_WALL, "Muro Nether", ShopItem.Category.NETHER, D*0.03);
        add(Material.NETHER_WART, "Verruca Nether", ShopItem.Category.NETHER, D*0.015);
        add(Material.NETHER_WART_BLOCK, "Blocco Verruca", ShopItem.Category.NETHER, D*0.012);
        add(Material.BLAZE_ROD, "Bastone Blaze", ShopItem.Category.NETHER, D*0.05);
        add(Material.BLAZE_POWDER, "Polvere Blaze", ShopItem.Category.NETHER, D*0.03);
        add(Material.GHAST_TEAR, "Lacrima Ghast", ShopItem.Category.NETHER, D*0.08);
        add(Material.MAGMA_CREAM, "Crema Magma", ShopItem.Category.NETHER, D*0.03);
        add(Material.SOUL_SAND, "Sabbia Anima", ShopItem.Category.NETHER, D*0.008);
        add(Material.SOUL_SOIL, "Terra Anima", ShopItem.Category.NETHER, D*0.006);
        add(Material.GLOWSTONE_DUST, "Polvere Glowstone", ShopItem.Category.NETHER, D*0.02);
        add(Material.WITHER_SKELETON_SKULL, "Teschio Wither", ShopItem.Category.NETHER, D*0.2);
        add(Material.CRIMSON_STEM, "Fungo Cremisi", ShopItem.Category.NETHER, D*0.005);
        add(Material.WARPED_STEM, "Fungo Deformato", ShopItem.Category.NETHER, D*0.005);
        add(Material.CRIMSON_PLANKS, "Assi Cremisi", ShopItem.Category.NETHER, D*0.004);
        add(Material.WARPED_PLANKS, "Assi Deformate", ShopItem.Category.NETHER, D*0.004);
        add(Material.CRIMSON_NYLIUM, "Nylium Cremisi", ShopItem.Category.NETHER, D*0.005);
        add(Material.WARPED_NYLIUM, "Nylium Deformato", ShopItem.Category.NETHER, D*0.005);
        add(Material.MAGMA_BLOCK, "Blocco Magma", ShopItem.Category.NETHER, D*0.009);
        add(Material.WEEPING_VINES, "Viti Piangenti", ShopItem.Category.NETHER, D*0.003);
        add(Material.TWISTING_VINES, "Viti Attorcigliate", ShopItem.Category.NETHER, D*0.003);
        add(Material.CRIMSON_ROOTS, "Radici Cremisi", ShopItem.Category.NETHER, D*0.002);
        add(Material.WARPED_ROOTS, "Radici Deformate", ShopItem.Category.NETHER, D*0.002);
        add(Material.CRIMSON_FUNGUS, "Fungo Cremisi", ShopItem.Category.NETHER, D*0.003);
        add(Material.WARPED_FUNGUS, "Fungo Deformato", ShopItem.Category.NETHER, D*0.003);
        add(Material.WARPED_WART_BLOCK, "Blocco Verruca Deformato", ShopItem.Category.NETHER, D*0.012);
        add(Material.GOLD_NUGGET, "Pepita Oro", ShopItem.Category.NETHER, D*0.011);
        add(Material.FIRE_CHARGE, "Palla di Fuoco", ShopItem.Category.NETHER, D*0.006);
        add(Material.BASALT, "Basalto", ShopItem.Category.NETHER, D*0.003);
        add(Material.BLACKSTONE, "Blackstone", ShopItem.Category.NETHER, D*0.004);

        // ============================================================
        // END
        // ============================================================
        double endstone_p = D*0.015;
        add(Material.END_STONE, "Pietra End", ShopItem.Category.END, endstone_p);
        add(Material.END_STONE_BRICKS, "Mattoni End", ShopItem.Category.END, endstone_p*1.1);
        add(Material.END_STONE_BRICK_STAIRS, "Scale End", ShopItem.Category.END, endstone_p*0.66);
        add(Material.END_STONE_BRICK_SLAB, "Lastra End", ShopItem.Category.END, endstone_p*0.33);
        add(Material.END_STONE_BRICK_WALL, "Muro End", ShopItem.Category.END, endstone_p*0.55);
        add(Material.PURPUR_BLOCK, "Purpur", ShopItem.Category.END, D*0.02);
        add(Material.PURPUR_PILLAR, "Pilastro Purpur", ShopItem.Category.END, D*0.022);
        add(Material.PURPUR_STAIRS, "Scale Purpur", ShopItem.Category.END, D*0.012);
        add(Material.PURPUR_SLAB, "Lastra Purpur", ShopItem.Category.END, D*0.006);
        add(Material.ENDER_PEARL, "Perla Ender", ShopItem.Category.END, D*0.05, false, 0, 16);
        add(Material.ENDER_EYE, "Occhio Ender", ShopItem.Category.END, D*0.1);
        add(Material.DRAGON_BREATH, "Respiro Drago", ShopItem.Category.END, D*0.15);
        add(Material.SHULKER_SHELL, "Guscio Shulker", ShopItem.Category.END, D*0.2);
        add(Material.CHORUS_FRUIT, "Frutto Coro", ShopItem.Category.END, D*0.02);
        add(Material.CHORUS_FLOWER, "Fiore Coro", ShopItem.Category.END, D*0.04);
        add(Material.POPPED_CHORUS_FRUIT, "Frutto Coro Esploso", ShopItem.Category.END, D*0.025);
        add(Material.END_ROD, "Bastone End", ShopItem.Category.END, D*0.012);
        add(Material.END_CRYSTAL, "Cristallo End", ShopItem.Category.END, D*25);
        add(Material.ECHO_SHARD, "Frammento Eco", ShopItem.Category.END, D*25);
        add(Material.ENDER_CHEST, "Forziere Ender", ShopItem.Category.END, D*20);

        // SCULK
        add(Material.SCULK, "Sculk", ShopItem.Category.END, D*0.012);
        add(Material.SCULK_SENSOR, "Sensore Sculk", ShopItem.Category.END, D*0.06);
        add(Material.CALIBRATED_SCULK_SENSOR, "Sensore Sculk Calibrato", ShopItem.Category.END, D*0.12);
        add(Material.SCULK_CATALYST, "Catalizzatore Sculk", ShopItem.Category.END, D*0.12);
        add(Material.SCULK_SHRIEKER, "Strillatore Sculk", ShopItem.Category.END, D*0.18);
        add(Material.SCULK_VEIN, "Vena Sculk", ShopItem.Category.END, D*0.008);

        // ============================================================
        // REDSTONE
        // ============================================================
        add(Material.REDSTONE, "Redstone", ShopItem.Category.REDSTONE, D*0.02);
        add(Material.REDSTONE_BLOCK, "Blocco Redstone", ShopItem.Category.REDSTONE, D*0.18);
        add(Material.REDSTONE_TORCH, "Torcia Redstone", ShopItem.Category.REDSTONE, D*0.003);
        add(Material.REDSTONE_LAMP, "Lampada Redstone", ShopItem.Category.REDSTONE, D*0.015);
        add(Material.REPEATER, "Ripetitore", ShopItem.Category.REDSTONE, D*0.006);
        add(Material.COMPARATOR, "Comparatore", ShopItem.Category.REDSTONE, D*0.009);
        add(Material.OBSERVER, "Osservatore", ShopItem.Category.REDSTONE, D*0.012);
        add(Material.PISTON, "Pistone", ShopItem.Category.REDSTONE, D*0.012);
        add(Material.STICKY_PISTON, "Pistone Appiccicoso", ShopItem.Category.REDSTONE, D*0.018);
        add(Material.SLIME_BLOCK, "Blocco Muco", ShopItem.Category.REDSTONE, D*0.02);
        add(Material.HONEY_BLOCK, "Blocco Miele", ShopItem.Category.REDSTONE, D*0.03);
        add(Material.DISPENSER, "Distributore", ShopItem.Category.REDSTONE, D*0.009);
        add(Material.DROPPER, "Espulsore", ShopItem.Category.REDSTONE, D*0.006);
        add(Material.HOPPER, "Tramoggia", ShopItem.Category.REDSTONE, D*0.024);
        add(Material.LEVER, "Leva", ShopItem.Category.REDSTONE, D*0.002);
        add(Material.STONE_BUTTON, "Pulsante Pietra", ShopItem.Category.REDSTONE, D*0.002);
        add(Material.STONE_PRESSURE_PLATE, "Piattaforma Pietra", ShopItem.Category.REDSTONE, D*0.003);
        add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, "Piattaforma Ferro", ShopItem.Category.REDSTONE, D*0.05);
        add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, "Piattaforma Oro", ShopItem.Category.REDSTONE, D*0.08);
        add(Material.TARGET, "Bersaglio", ShopItem.Category.REDSTONE, D*0.006);
        add(Material.DAYLIGHT_DETECTOR, "Rilevatore Luce", ShopItem.Category.REDSTONE, D*0.009);
        add(Material.TRIPWIRE_HOOK, "Gancio Filo", ShopItem.Category.REDSTONE, D*0.003);
        add(Material.TNT, "TNT", ShopItem.Category.REDSTONE, D*0.04);
        add(Material.MINECART, "Vagoncino", ShopItem.Category.REDSTONE, D*0.015, false, 0, 1);
        add(Material.CHEST_MINECART, "Vagoncino Forziere", ShopItem.Category.REDSTONE, D*0.02, false, 0, 1);
        add(Material.HOPPER_MINECART, "Vagoncino Tramoggia", ShopItem.Category.REDSTONE, D*0.025, false, 0, 1);
        add(Material.FURNACE_MINECART, "Vagoncino Fornace", ShopItem.Category.REDSTONE, D*0.018, false, 0, 1);
        add(Material.RAIL, "Binario", ShopItem.Category.REDSTONE, D*0.003);
        add(Material.POWERED_RAIL, "Binario Alimentato", ShopItem.Category.REDSTONE, D*0.008);
        add(Material.DETECTOR_RAIL, "Binario Rilevatore", ShopItem.Category.REDSTONE, D*0.006);
        add(Material.ACTIVATOR_RAIL, "Binario Attivatore", ShopItem.Category.REDSTONE, D*0.006);
        add(Material.COMPASS, "Bussola", ShopItem.Category.REDSTONE, D*0.012);
        add(Material.CLOCK, "Orologio", ShopItem.Category.REDSTONE, D*0.012);
        add(Material.FIREWORK_ROCKET, "Razzo", ShopItem.Category.REDSTONE, D*0.003);
        add(Material.FIREWORK_STAR, "Stella Razzo", ShopItem.Category.REDSTONE, D*0.005);
        add(Material.RESPAWN_ANCHOR, "Ancora Respawn", ShopItem.Category.REDSTONE, D*0.15);

        // ============================================================
        // RARI
        // ============================================================
        add(Material.TOTEM_OF_UNDYING, "Totem", ShopItem.Category.RARI, D*400, true, 7, 1);
        add(Material.ELYTRA, "Elitra", ShopItem.Category.RARI, D*800, true, 7, 1);
        add(Material.HEART_OF_THE_SEA, "Cuore del Mare", ShopItem.Category.RARI, D*160, true, 3, 1);
        add(Material.NETHER_STAR, "Stella Nether", ShopItem.Category.RARI, D*240, true, 3, 1);
        add(Material.TRIDENT, "Tridente", ShopItem.Category.RARI, D*100, true, 3, 1);
        add(Material.END_CRYSTAL, "Cristallo End", ShopItem.Category.RARI, D*25, false, 0, 1);
        add(Material.RECOVERY_COMPASS, "Bussola Recupero", ShopItem.Category.RARI, D*15, false, 0, 1);
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
