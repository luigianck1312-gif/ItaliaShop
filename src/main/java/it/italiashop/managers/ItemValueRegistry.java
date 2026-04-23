package it.italiashop.managers;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ItemValueRegistry {

    // T = acquisto terra x1 = 3,906 → 64 terra acquisto = 250,000
    // D = acquisto diamante x1 = 2,083,333 → vendita = 1,250,000
    // Elitra acquisto = 1,000,000,000
    private static final double T = 3_906;
    private static final double D = 2_083_333;
    private static final double DIRT_BUY = T; // alias

    private static final Map<Material, double[]> VALUES = new HashMap<>();

    static {
        // Prezzi: {acquisto, vendita=60%}
        // buyPrice=-1 = solo vendita

        // TERRA E ROCCIA
        v(Material.DIRT, T);
        v(Material.GRASS_BLOCK, T*1.25);
        v(Material.COARSE_DIRT, T);
        v(Material.PODZOL, T*1.5);
        v(Material.ROOTED_DIRT, T*1.25);
        v(Material.MUD, T);
        v(Material.PACKED_MUD, T*2);
        v(Material.MUD_BRICKS, T*2.5);
        v(Material.MUD_BRICK_STAIRS, T*1.5);
        v(Material.MUD_BRICK_SLAB, T*0.75);
        v(Material.MUD_BRICK_WALL, T*1.25);
        v(Material.SAND, T*1.5);
        v(Material.RED_SAND, T*1.6);
        v(Material.GRAVEL, T*1.75);
        v(Material.CLAY, T*3);
        v(Material.CLAY_BALL, T*0.75);
        v(Material.ICE, T*2.5);
        v(Material.PACKED_ICE, T*5);
        v(Material.BLUE_ICE, T*10);
        v(Material.SNOW_BLOCK, T*2);
        v(Material.SNOW, T*0.5);
        v(Material.OBSIDIAN, T*25);
        v(Material.CRYING_OBSIDIAN, T*32.5);
        v(Material.COBWEB, T*4);
        v(Material.SPONGE, T*250);
        v(Material.WET_SPONGE, -1, T*150*0.6);
        v(Material.DRIPSTONE_BLOCK, T*1.5);
        v(Material.POINTED_DRIPSTONE, T);
        v(Material.MYCELIUM, T*2);
        v(Material.DIRT_PATH, T);

        // PIETRA
        v(Material.STONE, T*2.1);
        v(Material.COBBLESTONE, T*1.6);
        v(Material.MOSSY_COBBLESTONE, T*2.1);
        v(Material.SMOOTH_STONE, T*2.7);
        v(Material.STONE_BRICKS, T*3.2);
        v(Material.MOSSY_STONE_BRICKS, T*3.7);
        v(Material.CRACKED_STONE_BRICKS, T*3.5);
        v(Material.CHISELED_STONE_BRICKS, T*3.7);
        v(Material.STONE_STAIRS, T*1.3);
        v(Material.STONE_SLAB, T*0.6);
        v(Material.STONE_BRICK_STAIRS, T*1.9);
        v(Material.STONE_BRICK_SLAB, T*1);
        v(Material.STONE_BRICK_WALL, T*1.6);
        v(Material.COBBLESTONE_STAIRS, T*1);
        v(Material.COBBLESTONE_SLAB, T*0.5);
        v(Material.COBBLESTONE_WALL, T*0.8);
        v(Material.MOSSY_COBBLESTONE_STAIRS, T*1.3);
        v(Material.MOSSY_COBBLESTONE_SLAB, T*0.6);
        v(Material.MOSSY_COBBLESTONE_WALL, T*1.1);
        v(Material.MOSSY_STONE_BRICK_STAIRS, T*2.2);
        v(Material.MOSSY_STONE_BRICK_SLAB, T*1.1);
        v(Material.MOSSY_STONE_BRICK_WALL, T*1.9);
        v(Material.SMOOTH_STONE_SLAB, T*1.3);
        v(Material.BRICKS, T*6.4);
        v(Material.BRICK_STAIRS, T*3.8);
        v(Material.BRICK_SLAB, T*1.9);
        v(Material.BRICK_WALL, T*3.2);
        v(Material.SANDSTONE, T*2.7);
        v(Material.SMOOTH_SANDSTONE, T*3.2);
        v(Material.CHISELED_SANDSTONE, T*3.7);
        v(Material.CUT_SANDSTONE, T*3.2);
        v(Material.SANDSTONE_STAIRS, T*1.6);
        v(Material.SANDSTONE_SLAB, T*0.8);
        v(Material.SANDSTONE_WALL, T*1.3);
        v(Material.SMOOTH_SANDSTONE_STAIRS, T*1.9);
        v(Material.SMOOTH_SANDSTONE_SLAB, T*1);
        v(Material.CUT_SANDSTONE_SLAB, T*1);
        v(Material.RED_SANDSTONE, T*2.7);
        v(Material.SMOOTH_RED_SANDSTONE, T*3.2);
        v(Material.CHISELED_RED_SANDSTONE, T*3.7);
        v(Material.CUT_RED_SANDSTONE, T*3.2);
        v(Material.RED_SANDSTONE_STAIRS, T*1.6);
        v(Material.RED_SANDSTONE_SLAB, T*0.8);
        v(Material.RED_SANDSTONE_WALL, T*1.3);
        v(Material.SMOOTH_RED_SANDSTONE_STAIRS, T*1.9);
        v(Material.SMOOTH_RED_SANDSTONE_SLAB, T*1);
        v(Material.ANDESITE, T*1.6);
        v(Material.POLISHED_ANDESITE, T*2.1);
        v(Material.ANDESITE_STAIRS, T*1);
        v(Material.ANDESITE_SLAB, T*0.5);
        v(Material.ANDESITE_WALL, T*0.8);
        v(Material.POLISHED_ANDESITE_STAIRS, T*1.3);
        v(Material.POLISHED_ANDESITE_SLAB, T*0.6);
        v(Material.DIORITE, T*1.6);
        v(Material.POLISHED_DIORITE, T*2.1);
        v(Material.DIORITE_STAIRS, T*1);
        v(Material.DIORITE_SLAB, T*0.5);
        v(Material.DIORITE_WALL, T*0.8);
        v(Material.POLISHED_DIORITE_STAIRS, T*1.3);
        v(Material.POLISHED_DIORITE_SLAB, T*0.6);
        v(Material.GRANITE, T*1.6);
        v(Material.POLISHED_GRANITE, T*2.1);
        v(Material.GRANITE_STAIRS, T*1);
        v(Material.GRANITE_SLAB, T*0.5);
        v(Material.GRANITE_WALL, T*0.8);
        v(Material.POLISHED_GRANITE_STAIRS, T*1.3);
        v(Material.POLISHED_GRANITE_SLAB, T*0.6);
        v(Material.DEEPSLATE, T*2.1);
        v(Material.COBBLED_DEEPSLATE, T*1.9);
        v(Material.POLISHED_DEEPSLATE, T*2.7);
        v(Material.DEEPSLATE_BRICKS, T*3.2);
        v(Material.DEEPSLATE_TILES, T*3.7);
        v(Material.CHISELED_DEEPSLATE, T*3.7);
        v(Material.CRACKED_DEEPSLATE_BRICKS, T*3.5);
        v(Material.CRACKED_DEEPSLATE_TILES, T*4);
        v(Material.COBBLED_DEEPSLATE_STAIRS, T*1.1);
        v(Material.COBBLED_DEEPSLATE_SLAB, T*0.6);
        v(Material.COBBLED_DEEPSLATE_WALL, T*0.9);
        v(Material.POLISHED_DEEPSLATE_STAIRS, T*1.6);
        v(Material.POLISHED_DEEPSLATE_SLAB, T*0.8);
        v(Material.POLISHED_DEEPSLATE_WALL, T*1.3);
        v(Material.DEEPSLATE_BRICK_STAIRS, T*1.9);
        v(Material.DEEPSLATE_BRICK_SLAB, T*1);
        v(Material.DEEPSLATE_BRICK_WALL, T*1.6);
        v(Material.DEEPSLATE_TILE_STAIRS, T*2.2);
        v(Material.DEEPSLATE_TILE_SLAB, T*1.1);
        v(Material.DEEPSLATE_TILE_WALL, T*1.9);
        v(Material.REINFORCED_DEEPSLATE, -1, T*266.7);
        v(Material.TUFF, T*1.6);
        v(Material.POLISHED_TUFF, T*2.1);
        v(Material.CHISELED_TUFF, T*2.7);
        v(Material.TUFF_BRICKS, T*2.7);
        v(Material.CHISELED_TUFF_BRICKS, T*3.2);
        v(Material.TUFF_STAIRS, T*1.6);
        v(Material.TUFF_SLAB, T*0.8);
        v(Material.TUFF_WALL, T*1.3);
        v(Material.POLISHED_TUFF_STAIRS, T*1.3);
        v(Material.POLISHED_TUFF_SLAB, T*0.6);
        v(Material.POLISHED_TUFF_WALL, T*1.1);
        v(Material.TUFF_BRICK_STAIRS, T*1.6);
        v(Material.TUFF_BRICK_SLAB, T*0.8);
        v(Material.TUFF_BRICK_WALL, T*1.3);
        v(Material.BASALT, T*1.6);
        v(Material.SMOOTH_BASALT, T*2.1);
        v(Material.POLISHED_BASALT, T*2.1);
        v(Material.CALCITE, T*1.6);
        v(Material.BLACKSTONE, T*2.1);
        v(Material.GILDED_BLACKSTONE, T*4.3);
        v(Material.POLISHED_BLACKSTONE, T*2.7);
        v(Material.CHISELED_POLISHED_BLACKSTONE, T*3.7);
        v(Material.POLISHED_BLACKSTONE_BRICKS, T*3.2);
        v(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, T*3.5);
        v(Material.BLACKSTONE_STAIRS, T*1.3);
        v(Material.BLACKSTONE_SLAB, T*0.6);
        v(Material.BLACKSTONE_WALL, T*1.1);
        v(Material.POLISHED_BLACKSTONE_STAIRS, T*1.6);
        v(Material.POLISHED_BLACKSTONE_SLAB, T*0.8);
        v(Material.POLISHED_BLACKSTONE_WALL, T*1.3);
        v(Material.POLISHED_BLACKSTONE_BRICK_STAIRS, T*1.9);
        v(Material.POLISHED_BLACKSTONE_BRICK_SLAB, T*1);
        v(Material.POLISHED_BLACKSTONE_BRICK_WALL, T*1.6);
        v(Material.POLISHED_BLACKSTONE_BUTTON, T*1.1);
        v(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, T*1.6);
        v(Material.PRISMARINE, T*5.3);
        v(Material.PRISMARINE_BRICKS, T*6.9);
        v(Material.DARK_PRISMARINE, T*8.5);
        v(Material.SEA_LANTERN, T*10.7);
        v(Material.PRISMARINE_STAIRS, T*3.2);
        v(Material.PRISMARINE_SLAB, T*1.6);
        v(Material.PRISMARINE_BRICK_STAIRS, T*4.2);
        v(Material.PRISMARINE_BRICK_SLAB, T*2.1);
        v(Material.DARK_PRISMARINE_STAIRS, T*5.1);
        v(Material.DARK_PRISMARINE_SLAB, T*2.6);
        v(Material.PRISMARINE_SHARD, T*1.6);
        v(Material.PRISMARINE_CRYSTALS, T*2.7);

        // LEGNO - tutte le varianti
        String[] woodTypes = {"OAK","BIRCH","SPRUCE","JUNGLE","ACACIA","DARK_OAK","MANGROVE","CHERRY"};
        for (String w : woodTypes) {
            double log = T*4.3;
            double plank = log*0.75;
            safeV(w+"_LOG", log);
            safeV(w+"_WOOD", log*0.9);
            safeV("STRIPPED_"+w+"_LOG", log*0.85);
            safeV("STRIPPED_"+w+"_WOOD", log*0.8);
            safeV(w+"_PLANKS", plank);
            safeV(w+"_STAIRS", plank*0.6);
            safeV(w+"_SLAB", plank*0.3);
            safeV(w+"_FENCE", plank*0.5);
            safeV(w+"_FENCE_GATE", plank*0.8);
            safeV(w+"_DOOR", plank*0.8);
            safeV(w+"_TRAPDOOR", plank*0.8);
            safeV(w+"_PRESSURE_PLATE", plank*0.4);
            safeV(w+"_BUTTON", plank*0.3);
            safeV(w+"_SIGN", plank*0.5);
            safeV(w+"_HANGING_SIGN", plank*0.6);
            safeV(w+"_LEAVES", T*1.1);
            safeV(w+"_SAPLING", T*1.6);
            safeV(w+"_BOAT", T*8);
            safeV(w+"_CHEST_BOAT", T*10.7);
        }
        v(Material.BAMBOO, T*1.6);
        v(Material.BAMBOO_BLOCK, T*4.3);
        v(Material.STRIPPED_BAMBOO_BLOCK, T*3.7);
        v(Material.BAMBOO_PLANKS, T*3.2);
        v(Material.BAMBOO_MOSAIC, T*3.7);
        v(Material.BAMBOO_STAIRS, T*1.9);
        v(Material.BAMBOO_SLAB, T*1);
        v(Material.BAMBOO_MOSAIC_STAIRS, T*2.2);
        v(Material.BAMBOO_MOSAIC_SLAB, T*1.1);
        v(Material.BAMBOO_FENCE, T*1.6);
        v(Material.BAMBOO_FENCE_GATE, T*2.6);
        v(Material.BAMBOO_DOOR, T*2.6);
        v(Material.BAMBOO_TRAPDOOR, T*2.6);
        v(Material.BAMBOO_PRESSURE_PLATE, T*1.3);
        v(Material.BAMBOO_BUTTON, T*1);
        v(Material.BAMBOO_SIGN, T*1.6);
        v(Material.BAMBOO_HANGING_SIGN, T*1.9);
        v(Material.BAMBOO_RAFT, T*8);
        v(Material.BAMBOO_CHEST_RAFT, T*10.7);

        // LANA COLORI
        String[] colors = {"WHITE","ORANGE","MAGENTA","LIGHT_BLUE","YELLOW","LIME","PINK","GRAY","LIGHT_GRAY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
        for (String c : colors) {
            safeV(c+"_WOOL", T*2.7);
            safeV(c+"_CARPET", T*1.1);
            safeV(c+"_CONCRETE", T*3.2);
            safeV(c+"_CONCRETE_POWDER", T*2.7);
            safeV(c+"_TERRACOTTA", T*3.7);
            safeV(c+"_GLAZED_TERRACOTTA", T*4.8);
            safeV(c+"_STAINED_GLASS", T*3.7);
            safeV(c+"_STAINED_GLASS_PANE", T*1.6);
            safeV(c+"_BED", T*5.3);
            safeV(c+"_BANNER", T*8);
            safeV(c+"_DYE", T*1.1);
            safeV(c+"_CANDLE", T*1.6);
            safeV(c+"_SHULKER_BOX", T*10667.3);
        }
        v(Material.TERRACOTTA, T*3.7);
        v(Material.GLASS, T*3.7);
        v(Material.GLASS_PANE, T*1.6);
        v(Material.TINTED_GLASS, T*5.3);
        v(Material.CANDLE, T*1.6);
        v(Material.SHULKER_BOX, T*10667.3);

        // DECORAZIONI
        v(Material.FLOWER_POT, T*1.1);
        v(Material.ITEM_FRAME, T*1.6);
        v(Material.GLOW_ITEM_FRAME, T*3.2);
        v(Material.PAINTING, T*1.6);
        v(Material.ARMOR_STAND, T*5.3);
        v(Material.BOOKSHELF, T*6.4);
        v(Material.CHISELED_BOOKSHELF, T*9.6);
        v(Material.DECORATED_POT, T*3.2);
        v(Material.TORCH, T*0.5);
        v(Material.SOUL_TORCH, T*0.8);
        v(Material.LANTERN, T*2.1);
        v(Material.SOUL_LANTERN, T*2.7);
        v(Material.END_ROD, T*6.4);
        v(Material.CAMPFIRE, T*4.3);
        v(Material.SOUL_CAMPFIRE, T*5.3);
        v(Material.GLOWSTONE, T*21.3);
        v(Material.SEA_LANTERN, T*10.7);
        v(Material.SHROOMLIGHT, T*10.7);
        v(Material.OCHRE_FROGLIGHT, T*21.3);
        v(Material.VERDANT_FROGLIGHT, T*21.3);
        v(Material.PEARLESCENT_FROGLIGHT, T*21.3);
        v(Material.REDSTONE_LAMP, T*8);
        v(Material.CHEST, T*4.3);
        v(Material.TRAPPED_CHEST, T*5.3);
        v(Material.BARREL, T*4.3);
        v(Material.CRAFTING_TABLE, T*3.2);
        v(Material.FURNACE, T*3.7);
        v(Material.SMOKER, T*6.4);
        v(Material.BLAST_FURNACE, T*8);
        v(Material.LOOM, T*4.3);
        v(Material.CARTOGRAPHY_TABLE, T*4.3);
        v(Material.FLETCHING_TABLE, T*4.3);
        v(Material.SMITHING_TABLE, T*4.3);
        v(Material.STONECUTTER, T*4.3);
        v(Material.GRINDSTONE, T*8);
        v(Material.COMPOSTER, T*3.2);
        v(Material.LECTERN, T*6.4);
        v(Material.JUKEBOX, T*32);
        v(Material.NOTE_BLOCK, T*3.7);
        v(Material.IRON_DOOR, T*80);
        v(Material.IRON_TRAPDOOR, T*80);

        // MINERALI
        v(Material.COAL, T*4.3);
        v(Material.COAL_ORE, T*5.3);
        v(Material.DEEPSLATE_COAL_ORE, T*5.9);
        v(Material.COAL_BLOCK, T*38.4);
        v(Material.IRON_INGOT, T*26.7);
        v(Material.RAW_IRON, T*24);
        v(Material.IRON_ORE, T*32);
        v(Material.DEEPSLATE_IRON_ORE, T*34.7);
        v(Material.IRON_NUGGET, T*2.9);
        v(Material.IRON_BLOCK, T*240);
        v(Material.GOLD_INGOT, T*53.3);
        v(Material.RAW_GOLD, T*48);
        v(Material.GOLD_ORE, T*64);
        v(Material.DEEPSLATE_GOLD_ORE, T*69.3);
        v(Material.GOLD_NUGGET, T*5.9);
        v(Material.GOLD_BLOCK, T*480);
        v(Material.DIAMOND, D);
        v(Material.DIAMOND_ORE, T*640);
        v(Material.DEEPSLATE_DIAMOND_ORE, T*693.4);
        v(Material.DIAMOND_BLOCK, T*4800.3);
        v(Material.EMERALD, T*320);
        v(Material.EMERALD_ORE, T*384);
        v(Material.DEEPSLATE_EMERALD_ORE, T*416);
        v(Material.EMERALD_BLOCK, T*2880.2);
        v(Material.LAPIS_LAZULI, T*16);
        v(Material.LAPIS_ORE, T*19.2);
        v(Material.DEEPSLATE_LAPIS_ORE, T*20.8);
        v(Material.LAPIS_BLOCK, T*144);
        v(Material.REDSTONE, T*10.7);
        v(Material.REDSTONE_ORE, T*12.8);
        v(Material.DEEPSLATE_REDSTONE_ORE, T*13.9);
        v(Material.REDSTONE_BLOCK, T*96);
        v(Material.QUARTZ, T*13.3);
        v(Material.NETHER_QUARTZ_ORE, T*16);
        v(Material.QUARTZ_BLOCK, T*53.3);
        v(Material.QUARTZ_BRICKS, T*58.7);
        v(Material.QUARTZ_PILLAR, T*58.7);
        v(Material.CHISELED_QUARTZ_BLOCK, T*58.7);
        v(Material.SMOOTH_QUARTZ, T*53.3);
        v(Material.QUARTZ_STAIRS, T*32);
        v(Material.QUARTZ_SLAB, T*16);
        v(Material.SMOOTH_QUARTZ_STAIRS, T*32);
        v(Material.SMOOTH_QUARTZ_SLAB, T*16);
        v(Material.COPPER_INGOT, T*8);
        v(Material.RAW_COPPER, T*6.9);
        v(Material.COPPER_ORE, T*9.6);
        v(Material.DEEPSLATE_COPPER_ORE, T*10.7);
        v(Material.COPPER_BLOCK, T*72);
        v(Material.AMETHYST_SHARD, T*21.3);
        v(Material.AMETHYST_BLOCK, T*85.3);
        v(Material.NETHER_GOLD_ORE, T*42.7);
        // Netherite - solo vendita
        v(Material.NETHERITE_INGOT, -1, T*4266.9);
        v(Material.NETHERITE_SCRAP, -1, T*1066.7);
        v(Material.ANCIENT_DEBRIS, -1, T*2133.5);
        v(Material.NETHERITE_BLOCK, -1, T*38402.5);

        // NATURA
        v(Material.SEAGRASS, T*1.1);
        v(Material.TALL_SEAGRASS, T*1.6);
        v(Material.KELP, T*0.8);
        v(Material.DRIED_KELP_BLOCK, T*4.3);
        v(Material.DRIED_KELP, T*0.5);
        v(Material.SUGAR_CANE, T*1.6);
        v(Material.CACTUS, T*2.1);
        v(Material.PUMPKIN, T*2.7);
        v(Material.CARVED_PUMPKIN, T*3.2);
        v(Material.JACK_O_LANTERN, T*4.3);
        v(Material.MELON_SLICE, T*0.2);
        v(Material.VINE, T*1.6);
        v(Material.LILY_PAD, T*2.7);
        v(Material.DANDELION, T*1.1);
        v(Material.POPPY, T*1.1);
        v(Material.BLUE_ORCHID, T*2.1);
        v(Material.ALLIUM, T*2.1);
        v(Material.AZURE_BLUET, T*1.6);
        v(Material.RED_TULIP, T*1.6);
        v(Material.ORANGE_TULIP, T*1.6);
        v(Material.WHITE_TULIP, T*1.6);
        v(Material.PINK_TULIP, T*1.6);
        v(Material.OXEYE_DAISY, T*1.6);
        v(Material.CORNFLOWER, T*1.6);
        v(Material.LILY_OF_THE_VALLEY, T*2.1);
        v(Material.WITHER_ROSE, T*21.3);
        v(Material.SUNFLOWER, T*2.7);
        v(Material.LILAC, T*2.7);
        v(Material.ROSE_BUSH, T*2.7);
        v(Material.PEONY, T*2.7);
        v(Material.TALL_GRASS, T*0.5);
        v(Material.LARGE_FERN, T*0.8);
        v(Material.FERN, T*0.5);
        v(Material.DEAD_BUSH, T*0.5);
        v(Material.RED_MUSHROOM, T*1.6);
        v(Material.BROWN_MUSHROOM, T*1.6);
        v(Material.MUSHROOM_STEM, T*1.6);
        v(Material.RED_MUSHROOM_BLOCK, T*1.6);
        v(Material.BROWN_MUSHROOM_BLOCK, T*1.6);
        v(Material.SPORE_BLOSSOM, T*6.4);
        v(Material.AZALEA, T*3.2);
        v(Material.FLOWERING_AZALEA, T*4.8);
        v(Material.AZALEA_LEAVES, T*1.6);
        v(Material.FLOWERING_AZALEA_LEAVES, T*2.1);
        v(Material.MOSS_BLOCK, T*2.7);
        v(Material.MOSS_CARPET, T*1.1);
        v(Material.BIG_DRIPLEAF, T*5.3);
        v(Material.SMALL_DRIPLEAF, T*3.7);
        v(Material.HANGING_ROOTS, T*1.6);
        v(Material.GLOW_LICHEN, T*2.1);
        v(Material.SWEET_BERRIES, T*1.6);
        v(Material.GLOW_BERRIES, T*2.1);
        v(Material.COCOA_BEANS, T*2.7);
        v(Material.WHEAT, T*1.6);
        v(Material.WHEAT_SEEDS, T*0.5);
        v(Material.POTATO, T*1.1);
        v(Material.CARROT, T*1.1);
        v(Material.BEETROOT, T*0.8);
        v(Material.BEETROOT_SEEDS, T*0.5);
        v(Material.PUMPKIN_SEEDS, T*0.8);
        v(Material.MELON_SEEDS, T*0.8);
        v(Material.APPLE, T*2.7);
        v(Material.TORCHFLOWER, T*10.7);
        v(Material.TORCHFLOWER_SEEDS, T*2.7);
        v(Material.PITCHER_PLANT, T*13.3);
        v(Material.PITCHER_POD, T*3.2);
        v(Material.PINK_PETALS, T*2.1);
        v(Material.STRING, T*3.2);
        v(Material.FEATHER, T*2.7);
        v(Material.LEATHER, T*5.3);
        v(Material.INK_SAC, T*4.3);
        v(Material.GLOW_INK_SAC, T*8.5);
        v(Material.SLIME_BALL, T*10.7);
        v(Material.MAGMA_CREAM, T*16);
        v(Material.SPIDER_EYE, T*4.8);
        v(Material.FERMENTED_SPIDER_EYE, T*8.5);
        v(Material.GUNPOWDER, T*6.4);
        v(Material.BONE, T*3.2);
        v(Material.BONE_MEAL, T*4.3);
        v(Material.BONE_BLOCK, T*38.4);
        v(Material.ROTTEN_FLESH, -1, T*0.5);
        v(Material.FLINT, T*1.6);
        v(Material.ARROW, T*1.6);
        v(Material.HONEYCOMB, T*4.8);
        v(Material.HONEYCOMB_BLOCK, T*19.2);
        v(Material.HONEY_BLOCK, T*16);
        v(Material.HONEY_BOTTLE, T*4.3);
        v(Material.EGG, T*1.1);
        v(Material.COD, T*1.6);
        v(Material.SALMON, T*2.1);
        v(Material.TROPICAL_FISH, T*2.7);
        v(Material.PUFFERFISH, T*3.2);
        v(Material.PHANTOM_MEMBRANE, T*16);
        v(Material.TURTLE_SCUTE, T*26.7);
        v(Material.NAUTILUS_SHELL, T*16);
        v(Material.RABBIT_HIDE, T*3.2);
        v(Material.RABBIT_FOOT, T*9.6);
        v(Material.CLAY_BALL, T*0.8);
        v(Material.SNOWBALL, T*0.3);
        v(Material.BREEZE_ROD, T*42.7);
        v(Material.ARMADILLO_SCUTE, T*26.7);
        v(Material.PAPER, T*1.1);
        v(Material.BOOK, T*3.2);
        v(Material.BUCKET, T*10.7);
        v(Material.WATER_BUCKET, T*12.8);
        v(Material.LAVA_BUCKET, T*17.1);
        v(Material.MILK_BUCKET, T*8);
        v(Material.SADDLE, T*266.7);
        v(Material.LEAD, T*4.8);
        v(Material.NAME_TAG, T*16);
        v(Material.BRUSH, T*5.3);
        v(Material.SPYGLASS, T*16);
        v(Material.GOAT_HORN, T*16);

        // NETHER
        v(Material.NETHERRACK, T*1.6);
        v(Material.NETHER_BRICK, T*2.7);
        v(Material.NETHER_BRICKS, T*32);
        v(Material.RED_NETHER_BRICKS, T*38.4);
        v(Material.CRACKED_NETHER_BRICKS, T*34.7);
        v(Material.CHISELED_NETHER_BRICKS, T*38.4);
        v(Material.NETHER_BRICK_STAIRS, T*19.2);
        v(Material.NETHER_BRICK_SLAB, T*9.6);
        v(Material.NETHER_BRICK_FENCE, T*16);
        v(Material.NETHER_BRICK_WALL, T*16);
        v(Material.RED_NETHER_BRICK_STAIRS, T*23);
        v(Material.RED_NETHER_BRICK_SLAB, T*11.5);
        v(Material.RED_NETHER_BRICK_WALL, T*19.2);
        v(Material.NETHER_WART, T*8);
        v(Material.NETHER_WART_BLOCK, T*6.4);
        v(Material.BLAZE_ROD, T*26.7);
        v(Material.BLAZE_POWDER, T*16);
        v(Material.GHAST_TEAR, T*42.7);
        v(Material.SOUL_SAND, T*4.3);
        v(Material.SOUL_SOIL, T*3.2);
        v(Material.GLOWSTONE_DUST, T*10.7);
        v(Material.WITHER_SKELETON_SKULL, T*106.7);
        v(Material.SKELETON_SKULL, -1, T*21.3);
        v(Material.ZOMBIE_HEAD, -1, T*16);
        v(Material.CREEPER_HEAD, -1, T*16);
        v(Material.PLAYER_HEAD, -1, T*10.7);
        v(Material.PIGLIN_HEAD, -1, T*16);
        v(Material.CRIMSON_STEM, T*2.7);
        v(Material.WARPED_STEM, T*2.7);
        v(Material.STRIPPED_CRIMSON_STEM, T*2.4);
        v(Material.STRIPPED_WARPED_STEM, T*2.4);
        v(Material.CRIMSON_HYPHAE, T*2.4);
        v(Material.WARPED_HYPHAE, T*2.4);
        v(Material.CRIMSON_PLANKS, T*2.1);
        v(Material.WARPED_PLANKS, T*2.1);
        v(Material.CRIMSON_STAIRS, T*1.3);
        v(Material.WARPED_STAIRS, T*1.3);
        v(Material.CRIMSON_SLAB, T*0.6);
        v(Material.WARPED_SLAB, T*0.6);
        v(Material.CRIMSON_FENCE, T*1.1);
        v(Material.WARPED_FENCE, T*1.1);
        v(Material.CRIMSON_FENCE_GATE, T*1.7);
        v(Material.WARPED_FENCE_GATE, T*1.7);
        v(Material.CRIMSON_DOOR, T*1.7);
        v(Material.WARPED_DOOR, T*1.7);
        v(Material.CRIMSON_TRAPDOOR, T*1.7);
        v(Material.WARPED_TRAPDOOR, T*1.7);
        v(Material.CRIMSON_PRESSURE_PLATE, T*0.9);
        v(Material.WARPED_PRESSURE_PLATE, T*0.9);
        v(Material.CRIMSON_BUTTON, T*0.6);
        v(Material.WARPED_BUTTON, T*0.6);
        v(Material.CRIMSON_SIGN, T*1.1);
        v(Material.WARPED_SIGN, T*1.1);
        v(Material.CRIMSON_NYLIUM, T*2.7);
        v(Material.WARPED_NYLIUM, T*2.7);
        v(Material.SHROOMLIGHT, T*10.7);
        v(Material.MAGMA_BLOCK, T*4.8);
        v(Material.WEEPING_VINES, T*1.6);
        v(Material.TWISTING_VINES, T*1.6);
        v(Material.CRIMSON_ROOTS, T*1.1);
        v(Material.WARPED_ROOTS, T*1.1);
        v(Material.CRIMSON_FUNGUS, T*1.6);
        v(Material.WARPED_FUNGUS, T*1.6);
        v(Material.WARPED_WART_BLOCK, T*6.4);
        v(Material.GOLD_NUGGET, T*5.9);
        v(Material.FIRE_CHARGE, T*3.2);
        v(Material.BASALT, T*1.6);
        v(Material.SMOOTH_BASALT, T*2.1);
        v(Material.POLISHED_BASALT, T*2.1);
        v(Material.BLACKSTONE, T*2.1);
        v(Material.GILDED_BLACKSTONE, T*4.3);

        // END
        v(Material.END_STONE, T*8);
        v(Material.END_STONE_BRICKS, T*9.6);
        v(Material.END_STONE_BRICK_STAIRS, T*5.9);
        v(Material.END_STONE_BRICK_SLAB, T*2.9);
        v(Material.END_STONE_BRICK_WALL, T*4.8);
        v(Material.PURPUR_BLOCK, T*10.7);
        v(Material.PURPUR_PILLAR, T*11.7);
        v(Material.PURPUR_STAIRS, T*6.4);
        v(Material.PURPUR_SLAB, T*3.2);
        v(Material.ENDER_PEARL, T*26.7);
        v(Material.ENDER_EYE, T*53.3);
        v(Material.DRAGON_BREATH, T*80);
        v(Material.SHULKER_SHELL, T*106.7);
        v(Material.CHORUS_FRUIT, T*10.7);
        v(Material.CHORUS_FLOWER, T*21.3);
        v(Material.POPPED_CHORUS_FRUIT, T*13.3);
        v(Material.CHORUS_PLANT, T*10.7);
        v(Material.END_ROD, T*6.4);
        v(Material.END_CRYSTAL, T*13334.2);
        v(Material.ECHO_SHARD, T*13334.2);
        v(Material.ENDER_CHEST, T*10667.3);
        v(Material.SCULK, T*6.4);
        v(Material.SCULK_SENSOR, T*32);
        v(Material.CALIBRATED_SCULK_SENSOR, T*64);
        v(Material.SCULK_CATALYST, T*64);
        v(Material.SCULK_SHRIEKER, T*96);
        v(Material.SCULK_VEIN, T*4.3);

        // REDSTONE
        v(Material.REDSTONE, T*10.7);
        v(Material.REDSTONE_BLOCK, T*96);
        v(Material.REDSTONE_TORCH, T*1.6);
        v(Material.REDSTONE_LAMP, T*8);
        v(Material.REPEATER, T*3.2);
        v(Material.COMPARATOR, T*4.8);
        v(Material.OBSERVER, T*6.4);
        v(Material.PISTON, T*6.4);
        v(Material.STICKY_PISTON, T*9.6);
        v(Material.SLIME_BLOCK, T*10.7);
        v(Material.HONEY_BLOCK, T*16);
        v(Material.DISPENSER, T*4.8);
        v(Material.DROPPER, T*3.2);
        v(Material.HOPPER, T*12.8);
        v(Material.LEVER, T*1.1);
        v(Material.STONE_BUTTON, T*1.1);
        v(Material.STONE_PRESSURE_PLATE, T*1.6);
        v(Material.OAK_BUTTON, T*1.1);
        v(Material.OAK_PRESSURE_PLATE, T*1.6);
        v(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, T*26.7);
        v(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, T*42.7);
        v(Material.TARGET, T*3.2);
        v(Material.DAYLIGHT_DETECTOR, T*4.8);
        v(Material.TRIPWIRE_HOOK, T*1.6);
        v(Material.TNT, T*21.3);
        v(Material.MINECART, T*8);
        v(Material.CHEST_MINECART, T*10.7);
        v(Material.HOPPER_MINECART, T*13.3);
        v(Material.FURNACE_MINECART, T*9.6);
        v(Material.RAIL, T*1.6);
        v(Material.POWERED_RAIL, T*4.3);
        v(Material.DETECTOR_RAIL, T*3.2);
        v(Material.ACTIVATOR_RAIL, T*3.2);
        v(Material.COMPASS, T*6.4);
        v(Material.CLOCK, T*6.4);
        v(Material.FIREWORK_ROCKET, T*1.6);
        v(Material.FIREWORK_STAR, T*2.7);
        v(Material.RESPAWN_ANCHOR, T*80);
        v(Material.LODESTONE, T*42.7);
        v(Material.LIGHTNING_ROD, T*26.7);
        v(Material.SCULK_SENSOR, T*32);
        v(Material.CALIBRATED_SCULK_SENSOR, T*64);

        // STRUMENTI - solo vendita
        v(Material.WOODEN_SWORD, -1, T*1.1); v(Material.STONE_SWORD, -1, T*2.1);
        v(Material.IRON_SWORD, -1, T*21.3); v(Material.GOLDEN_SWORD, -1, T*13.3);
        v(Material.DIAMOND_SWORD, -1, T*213.3); v(Material.NETHERITE_SWORD, -1, T*853.4);
        v(Material.WOODEN_PICKAXE, -1, T*1.1); v(Material.STONE_PICKAXE, -1, T*2.1);
        v(Material.IRON_PICKAXE, -1, T*21.3); v(Material.GOLDEN_PICKAXE, -1, T*13.3);
        v(Material.DIAMOND_PICKAXE, -1, T*213.3); v(Material.NETHERITE_PICKAXE, -1, T*853.4);
        v(Material.WOODEN_AXE, -1, T*1.1); v(Material.STONE_AXE, -1, T*2.1);
        v(Material.IRON_AXE, -1, T*21.3); v(Material.GOLDEN_AXE, -1, T*13.3);
        v(Material.DIAMOND_AXE, -1, T*213.3); v(Material.NETHERITE_AXE, -1, T*853.4);
        v(Material.WOODEN_SHOVEL, -1, T*1.1); v(Material.STONE_SHOVEL, -1, T*2.1);
        v(Material.IRON_SHOVEL, -1, T*21.3); v(Material.GOLDEN_SHOVEL, -1, T*13.3);
        v(Material.DIAMOND_SHOVEL, -1, T*213.3); v(Material.NETHERITE_SHOVEL, -1, T*853.4);
        v(Material.WOODEN_HOE, -1, T*1.1); v(Material.STONE_HOE, -1, T*2.1);
        v(Material.IRON_HOE, -1, T*21.3); v(Material.GOLDEN_HOE, -1, T*13.3);
        v(Material.DIAMOND_HOE, -1, T*213.3); v(Material.NETHERITE_HOE, -1, T*853.4);
        v(Material.BOW, -1, T*16); v(Material.CROSSBOW, -1, T*26.7);
        v(Material.SHIELD, -1, T*32); v(Material.FISHING_ROD, -1, T*8);
        v(Material.SHEARS, -1, T*13.3); v(Material.FLINT_AND_STEEL, -1, T*6.4);
        v(Material.TRIDENT, -1, T*53336.7);

        // ARMATURE - solo vendita
        v(Material.LEATHER_HELMET, -1, T*2.7); v(Material.LEATHER_CHESTPLATE, -1, T*4.3);
        v(Material.LEATHER_LEGGINGS, -1, T*3.7); v(Material.LEATHER_BOOTS, -1, T*2.7);
        v(Material.IRON_HELMET, -1, T*26.7); v(Material.IRON_CHESTPLATE, -1, T*42.7);
        v(Material.IRON_LEGGINGS, -1, T*37.3); v(Material.IRON_BOOTS, -1, T*26.7);
        v(Material.GOLDEN_HELMET, -1, T*16); v(Material.GOLDEN_CHESTPLATE, -1, T*26.7);
        v(Material.GOLDEN_LEGGINGS, -1, T*24); v(Material.GOLDEN_BOOTS, -1, T*16);
        v(Material.CHAINMAIL_HELMET, -1, T*18.7); v(Material.CHAINMAIL_CHESTPLATE, -1, T*32);
        v(Material.CHAINMAIL_LEGGINGS, -1, T*26.7); v(Material.CHAINMAIL_BOOTS, -1, T*18.7);
        v(Material.DIAMOND_HELMET, -1, T*266.7); v(Material.DIAMOND_CHESTPLATE, -1, T*426.7);
        v(Material.DIAMOND_LEGGINGS, -1, T*373.4); v(Material.DIAMOND_BOOTS, -1, T*266.7);
        v(Material.NETHERITE_HELMET, -1, T*1066.7); v(Material.NETHERITE_CHESTPLATE, -1, T*1706.8);
        v(Material.NETHERITE_LEGGINGS, -1, T*1493.4); v(Material.NETHERITE_BOOTS, -1, T*1066.7);
        v(Material.TURTLE_HELMET, -1, T*80);
        v(Material.WOLF_ARMOR, -1, T*106.7);

        // CIBO - solo vendita
        v(Material.BREAD, -1, T*1.1); v(Material.BEEF, -1, T*0.8);
        v(Material.COOKED_BEEF, -1, T*2.1); v(Material.CHICKEN, -1, T*0.5);
        v(Material.COOKED_CHICKEN, -1, T*1.6); v(Material.PORKCHOP, -1, T*0.8);
        v(Material.COOKED_PORKCHOP, -1, T*2.1); v(Material.MUTTON, -1, T*0.7);
        v(Material.COOKED_MUTTON, -1, T*1.9); v(Material.RABBIT, -1, T*0.5);
        v(Material.COOKED_RABBIT, -1, T*1.6); v(Material.COOKED_COD, -1, T*1.3);
        v(Material.COOKED_SALMON, -1, T*1.6); v(Material.BAKED_POTATO, -1, T*1.1);
        v(Material.MUSHROOM_STEW, -1, T*2.7); v(Material.RABBIT_STEW, -1, T*3.7);
        v(Material.BEETROOT_SOUP, -1, T*2.7); v(Material.PUMPKIN_PIE, -1, T*2.7);
        v(Material.CAKE, -1, T*5.3); v(Material.COOKIE, -1, T*0.5);
        v(Material.GOLDEN_APPLE, -1, T*42.7); v(Material.ENCHANTED_GOLDEN_APPLE, -1, T*2133.5);
        v(Material.GOLDEN_CARROT, -1, T*21.3); v(Material.HONEY_BOTTLE, T*4.3);

        // POZIONI - solo vendita
        v(Material.POTION, -1, T*8.5); v(Material.SPLASH_POTION, -1, T*10.7);
        v(Material.LINGERING_POTION, -1, T*14.9); v(Material.EXPERIENCE_BOTTLE, -1, T*21.3);

        // LIBRI - solo vendita
        v(Material.ENCHANTED_BOOK, -1, T*42.7); v(Material.KNOWLEDGE_BOOK, -1, T*213.3);
        v(Material.WRITTEN_BOOK, -1, T*2.1); v(Material.WRITABLE_BOOK, T*3.2);

        // DISCHI - solo vendita
        v(Material.MUSIC_DISC_13, -1, T*128); v(Material.MUSIC_DISC_CAT, -1, T*128);
        v(Material.MUSIC_DISC_BLOCKS, -1, T*128); v(Material.MUSIC_DISC_CHIRP, -1, T*128);
        v(Material.MUSIC_DISC_FAR, -1, T*128); v(Material.MUSIC_DISC_MALL, -1, T*128);
        v(Material.MUSIC_DISC_MELLOHI, -1, T*128); v(Material.MUSIC_DISC_STAL, -1, T*128);
        v(Material.MUSIC_DISC_STRAD, -1, T*128); v(Material.MUSIC_DISC_WARD, -1, T*128);
        v(Material.MUSIC_DISC_11, -1, T*213.3); v(Material.MUSIC_DISC_WAIT, -1, T*128);
        v(Material.MUSIC_DISC_OTHERSIDE, -1, T*213.3); v(Material.MUSIC_DISC_5, -1, T*426.7);
        v(Material.MUSIC_DISC_PIGSTEP, -1, T*341.4); v(Material.MUSIC_DISC_RELIC, -1, T*213.3);
        v(Material.MUSIC_DISC_CREATOR, -1, T*256); v(Material.MUSIC_DISC_PRECIPICE, -1, T*256);
        v(Material.DISC_FRAGMENT_5, -1, T*85.3);

        // RARI
        v(Material.TOTEM_OF_UNDYING, 500_000_000);
        v(Material.ELYTRA, 1_000_000_000);
        v(Material.HEART_OF_THE_SEA, 200_000_000);
        v(Material.NETHER_STAR, 300_000_000);
        v(Material.BEACON, -1, 180_000_000);
        v(Material.CONDUIT, -1, 60_000_000);
        v(Material.RECOVERY_COMPASS, 30_000_000);
        v(Material.ECHO_SHARD, T*13334.2);
        v(Material.DRAGON_EGG, -1, 600_000_000);
    }

    private static void v(Material mat, double buy) {
        double sell = buy * 0.6;
        VALUES.put(mat, new double[]{buy, sell});
    }

    private static void v(Material mat, double buy, double sell) {
        // Se buy=-1 usa sell direttamente, altrimenti sell=60%
        double actualSell = buy > 0 ? buy * 0.6 : sell;
        VALUES.put(mat, new double[]{buy, actualSell});
    }

    private static void safeV(String name, double buy) {
        try {
            Material mat = Material.valueOf(name);
            v(mat, buy);
        } catch (Exception ignored) {}
    }

    public static double getBuyPrice(Material mat) {
        double[] v = VALUES.get(mat);
        return v != null ? v[0] : -1;
    }

    public static double getSellPrice(Material mat) {
        double[] v = VALUES.get(mat);
        return v != null ? v[1] : -1;
    }

    public static boolean hasValue(Material mat) {
        return VALUES.containsKey(mat);
    }
}
