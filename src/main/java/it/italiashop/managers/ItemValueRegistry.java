package it.italiashop.managers;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ItemValueRegistry {

    // D = 1 diamante = 1,250,000$
    // Elitra = 1,000,000,000$
    // 64 diamanti = 80,000,000$
    private static final double D = 1_250_000;

    private static final Map<Material, double[]> VALUES = new HashMap<>();

    static {
        // Prezzi: {acquisto, vendita=60%}
        // buyPrice=-1 = solo vendita

        // TERRA E ROCCIA
        v(Material.DIRT, D*0.002);
        v(Material.GRASS_BLOCK, D*0.0025);
        v(Material.COARSE_DIRT, D*0.002);
        v(Material.PODZOL, D*0.003);
        v(Material.ROOTED_DIRT, D*0.0025);
        v(Material.MUD, D*0.002);
        v(Material.PACKED_MUD, D*0.004);
        v(Material.MUD_BRICKS, D*0.005);
        v(Material.MUD_BRICK_STAIRS, D*0.003);
        v(Material.MUD_BRICK_SLAB, D*0.0015);
        v(Material.MUD_BRICK_WALL, D*0.0025);
        v(Material.SAND, D*0.003);
        v(Material.RED_SAND, D*0.0032);
        v(Material.GRAVEL, D*0.0035);
        v(Material.CLAY, D*0.006);
        v(Material.CLAY_BALL, D*0.0015);
        v(Material.ICE, D*0.005);
        v(Material.PACKED_ICE, D*0.01);
        v(Material.BLUE_ICE, D*0.02);
        v(Material.SNOW_BLOCK, D*0.004);
        v(Material.SNOW, D*0.001);
        v(Material.OBSIDIAN, D*0.05);
        v(Material.CRYING_OBSIDIAN, D*0.065);
        v(Material.COBWEB, D*0.008);
        v(Material.SPONGE, D*0.5);
        v(Material.WET_SPONGE, -1, D*0.3);
        v(Material.DRIPSTONE_BLOCK, D*0.003);
        v(Material.POINTED_DRIPSTONE, D*0.002);
        v(Material.MYCELIUM, D*0.004);
        v(Material.DIRT_PATH, D*0.002);

        // PIETRA
        v(Material.STONE, D*0.004);
        v(Material.COBBLESTONE, D*0.003);
        v(Material.MOSSY_COBBLESTONE, D*0.004);
        v(Material.SMOOTH_STONE, D*0.005);
        v(Material.STONE_BRICKS, D*0.006);
        v(Material.MOSSY_STONE_BRICKS, D*0.007);
        v(Material.CRACKED_STONE_BRICKS, D*0.0065);
        v(Material.CHISELED_STONE_BRICKS, D*0.007);
        v(Material.STONE_STAIRS, D*0.0024);
        v(Material.STONE_SLAB, D*0.0012);
        v(Material.STONE_BRICK_STAIRS, D*0.0036);
        v(Material.STONE_BRICK_SLAB, D*0.0018);
        v(Material.STONE_BRICK_WALL, D*0.003);
        v(Material.COBBLESTONE_STAIRS, D*0.0018);
        v(Material.COBBLESTONE_SLAB, D*0.0009);
        v(Material.COBBLESTONE_WALL, D*0.0015);
        v(Material.MOSSY_COBBLESTONE_STAIRS, D*0.0024);
        v(Material.MOSSY_COBBLESTONE_SLAB, D*0.0012);
        v(Material.MOSSY_COBBLESTONE_WALL, D*0.002);
        v(Material.MOSSY_STONE_BRICK_STAIRS, D*0.0042);
        v(Material.MOSSY_STONE_BRICK_SLAB, D*0.0021);
        v(Material.MOSSY_STONE_BRICK_WALL, D*0.0035);
        v(Material.SMOOTH_STONE_SLAB, D*0.0025);
        v(Material.BRICKS, D*0.012);
        v(Material.BRICK_STAIRS, D*0.0072);
        v(Material.BRICK_SLAB, D*0.0036);
        v(Material.BRICK_WALL, D*0.006);
        v(Material.SANDSTONE, D*0.005);
        v(Material.SMOOTH_SANDSTONE, D*0.006);
        v(Material.CHISELED_SANDSTONE, D*0.007);
        v(Material.CUT_SANDSTONE, D*0.006);
        v(Material.SANDSTONE_STAIRS, D*0.003);
        v(Material.SANDSTONE_SLAB, D*0.0015);
        v(Material.SANDSTONE_WALL, D*0.0025);
        v(Material.SMOOTH_SANDSTONE_STAIRS, D*0.0036);
        v(Material.SMOOTH_SANDSTONE_SLAB, D*0.0018);
        v(Material.CUT_SANDSTONE_SLAB, D*0.0018);
        v(Material.RED_SANDSTONE, D*0.005);
        v(Material.SMOOTH_RED_SANDSTONE, D*0.006);
        v(Material.CHISELED_RED_SANDSTONE, D*0.007);
        v(Material.CUT_RED_SANDSTONE, D*0.006);
        v(Material.RED_SANDSTONE_STAIRS, D*0.003);
        v(Material.RED_SANDSTONE_SLAB, D*0.0015);
        v(Material.RED_SANDSTONE_WALL, D*0.0025);
        v(Material.SMOOTH_RED_SANDSTONE_STAIRS, D*0.0036);
        v(Material.SMOOTH_RED_SANDSTONE_SLAB, D*0.0018);
        v(Material.ANDESITE, D*0.003);
        v(Material.POLISHED_ANDESITE, D*0.004);
        v(Material.ANDESITE_STAIRS, D*0.0018);
        v(Material.ANDESITE_SLAB, D*0.0009);
        v(Material.ANDESITE_WALL, D*0.0015);
        v(Material.POLISHED_ANDESITE_STAIRS, D*0.0024);
        v(Material.POLISHED_ANDESITE_SLAB, D*0.0012);
        v(Material.DIORITE, D*0.003);
        v(Material.POLISHED_DIORITE, D*0.004);
        v(Material.DIORITE_STAIRS, D*0.0018);
        v(Material.DIORITE_SLAB, D*0.0009);
        v(Material.DIORITE_WALL, D*0.0015);
        v(Material.POLISHED_DIORITE_STAIRS, D*0.0024);
        v(Material.POLISHED_DIORITE_SLAB, D*0.0012);
        v(Material.GRANITE, D*0.003);
        v(Material.POLISHED_GRANITE, D*0.004);
        v(Material.GRANITE_STAIRS, D*0.0018);
        v(Material.GRANITE_SLAB, D*0.0009);
        v(Material.GRANITE_WALL, D*0.0015);
        v(Material.POLISHED_GRANITE_STAIRS, D*0.0024);
        v(Material.POLISHED_GRANITE_SLAB, D*0.0012);
        v(Material.DEEPSLATE, D*0.004);
        v(Material.COBBLED_DEEPSLATE, D*0.0035);
        v(Material.POLISHED_DEEPSLATE, D*0.005);
        v(Material.DEEPSLATE_BRICKS, D*0.006);
        v(Material.DEEPSLATE_TILES, D*0.007);
        v(Material.CHISELED_DEEPSLATE, D*0.007);
        v(Material.CRACKED_DEEPSLATE_BRICKS, D*0.0065);
        v(Material.CRACKED_DEEPSLATE_TILES, D*0.0075);
        v(Material.COBBLED_DEEPSLATE_STAIRS, D*0.0021);
        v(Material.COBBLED_DEEPSLATE_SLAB, D*0.00105);
        v(Material.COBBLED_DEEPSLATE_WALL, D*0.00175);
        v(Material.POLISHED_DEEPSLATE_STAIRS, D*0.003);
        v(Material.POLISHED_DEEPSLATE_SLAB, D*0.0015);
        v(Material.POLISHED_DEEPSLATE_WALL, D*0.0025);
        v(Material.DEEPSLATE_BRICK_STAIRS, D*0.0036);
        v(Material.DEEPSLATE_BRICK_SLAB, D*0.0018);
        v(Material.DEEPSLATE_BRICK_WALL, D*0.003);
        v(Material.DEEPSLATE_TILE_STAIRS, D*0.0042);
        v(Material.DEEPSLATE_TILE_SLAB, D*0.0021);
        v(Material.DEEPSLATE_TILE_WALL, D*0.0035);
        v(Material.REINFORCED_DEEPSLATE, -1, D*0.5);
        v(Material.TUFF, D*0.003);
        v(Material.POLISHED_TUFF, D*0.004);
        v(Material.CHISELED_TUFF, D*0.005);
        v(Material.TUFF_BRICKS, D*0.005);
        v(Material.CHISELED_TUFF_BRICKS, D*0.006);
        v(Material.TUFF_STAIRS, D*0.003);
        v(Material.TUFF_SLAB, D*0.0015);
        v(Material.TUFF_WALL, D*0.0025);
        v(Material.POLISHED_TUFF_STAIRS, D*0.0024);
        v(Material.POLISHED_TUFF_SLAB, D*0.0012);
        v(Material.POLISHED_TUFF_WALL, D*0.002);
        v(Material.TUFF_BRICK_STAIRS, D*0.003);
        v(Material.TUFF_BRICK_SLAB, D*0.0015);
        v(Material.TUFF_BRICK_WALL, D*0.0025);
        v(Material.BASALT, D*0.003);
        v(Material.SMOOTH_BASALT, D*0.004);
        v(Material.POLISHED_BASALT, D*0.004);
        v(Material.CALCITE, D*0.003);
        v(Material.BLACKSTONE, D*0.004);
        v(Material.GILDED_BLACKSTONE, D*0.008);
        v(Material.POLISHED_BLACKSTONE, D*0.005);
        v(Material.CHISELED_POLISHED_BLACKSTONE, D*0.007);
        v(Material.POLISHED_BLACKSTONE_BRICKS, D*0.006);
        v(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, D*0.0065);
        v(Material.BLACKSTONE_STAIRS, D*0.0024);
        v(Material.BLACKSTONE_SLAB, D*0.0012);
        v(Material.BLACKSTONE_WALL, D*0.002);
        v(Material.POLISHED_BLACKSTONE_STAIRS, D*0.003);
        v(Material.POLISHED_BLACKSTONE_SLAB, D*0.0015);
        v(Material.POLISHED_BLACKSTONE_WALL, D*0.0025);
        v(Material.POLISHED_BLACKSTONE_BRICK_STAIRS, D*0.0036);
        v(Material.POLISHED_BLACKSTONE_BRICK_SLAB, D*0.0018);
        v(Material.POLISHED_BLACKSTONE_BRICK_WALL, D*0.003);
        v(Material.POLISHED_BLACKSTONE_BUTTON, D*0.002);
        v(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, D*0.003);
        v(Material.PRISMARINE, D*0.01);
        v(Material.PRISMARINE_BRICKS, D*0.013);
        v(Material.DARK_PRISMARINE, D*0.016);
        v(Material.SEA_LANTERN, D*0.02);
        v(Material.PRISMARINE_STAIRS, D*0.006);
        v(Material.PRISMARINE_SLAB, D*0.003);
        v(Material.PRISMARINE_BRICK_STAIRS, D*0.0078);
        v(Material.PRISMARINE_BRICK_SLAB, D*0.0039);
        v(Material.DARK_PRISMARINE_STAIRS, D*0.0096);
        v(Material.DARK_PRISMARINE_SLAB, D*0.0048);
        v(Material.PRISMARINE_SHARD, D*0.003);
        v(Material.PRISMARINE_CRYSTALS, D*0.005);

        // LEGNO - tutte le varianti
        String[] woodTypes = {"OAK","BIRCH","SPRUCE","JUNGLE","ACACIA","DARK_OAK","MANGROVE","CHERRY"};
        for (String w : woodTypes) {
            double log = D*0.008;
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
            safeV(w+"_LEAVES", D*0.002);
            safeV(w+"_SAPLING", D*0.003);
            safeV(w+"_BOAT", D*0.015);
            safeV(w+"_CHEST_BOAT", D*0.02);
        }
        v(Material.BAMBOO, D*0.003);
        v(Material.BAMBOO_BLOCK, D*0.008);
        v(Material.STRIPPED_BAMBOO_BLOCK, D*0.007);
        v(Material.BAMBOO_PLANKS, D*0.006);
        v(Material.BAMBOO_MOSAIC, D*0.007);
        v(Material.BAMBOO_STAIRS, D*0.0036);
        v(Material.BAMBOO_SLAB, D*0.0018);
        v(Material.BAMBOO_MOSAIC_STAIRS, D*0.0042);
        v(Material.BAMBOO_MOSAIC_SLAB, D*0.0021);
        v(Material.BAMBOO_FENCE, D*0.003);
        v(Material.BAMBOO_FENCE_GATE, D*0.0048);
        v(Material.BAMBOO_DOOR, D*0.0048);
        v(Material.BAMBOO_TRAPDOOR, D*0.0048);
        v(Material.BAMBOO_PRESSURE_PLATE, D*0.0024);
        v(Material.BAMBOO_BUTTON, D*0.0018);
        v(Material.BAMBOO_SIGN, D*0.003);
        v(Material.BAMBOO_HANGING_SIGN, D*0.0036);
        v(Material.BAMBOO_RAFT, D*0.015);
        v(Material.BAMBOO_CHEST_RAFT, D*0.02);

        // LANA COLORI
        String[] colors = {"WHITE","ORANGE","MAGENTA","LIGHT_BLUE","YELLOW","LIME","PINK","GRAY","LIGHT_GRAY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
        for (String c : colors) {
            safeV(c+"_WOOL", D*0.005);
            safeV(c+"_CARPET", D*0.002);
            safeV(c+"_CONCRETE", D*0.006);
            safeV(c+"_CONCRETE_POWDER", D*0.005);
            safeV(c+"_TERRACOTTA", D*0.007);
            safeV(c+"_GLAZED_TERRACOTTA", D*0.009);
            safeV(c+"_STAINED_GLASS", D*0.007);
            safeV(c+"_STAINED_GLASS_PANE", D*0.003);
            safeV(c+"_BED", D*0.01);
            safeV(c+"_BANNER", D*0.015);
            safeV(c+"_DYE", D*0.002);
            safeV(c+"_CANDLE", D*0.003);
            safeV(c+"_SHULKER_BOX", D*20);
        }
        v(Material.TERRACOTTA, D*0.007);
        v(Material.GLASS, D*0.007);
        v(Material.GLASS_PANE, D*0.003);
        v(Material.TINTED_GLASS, D*0.01);
        v(Material.CANDLE, D*0.003);
        v(Material.SHULKER_BOX, D*20);

        // DECORAZIONI
        v(Material.FLOWER_POT, D*0.002);
        v(Material.ITEM_FRAME, D*0.003);
        v(Material.GLOW_ITEM_FRAME, D*0.006);
        v(Material.PAINTING, D*0.003);
        v(Material.ARMOR_STAND, D*0.01);
        v(Material.BOOKSHELF, D*0.012);
        v(Material.CHISELED_BOOKSHELF, D*0.018);
        v(Material.DECORATED_POT, D*0.006);
        v(Material.TORCH, D*0.001);
        v(Material.SOUL_TORCH, D*0.0015);
        v(Material.LANTERN, D*0.004);
        v(Material.SOUL_LANTERN, D*0.005);
        v(Material.END_ROD, D*0.012);
        v(Material.CAMPFIRE, D*0.008);
        v(Material.SOUL_CAMPFIRE, D*0.01);
        v(Material.GLOWSTONE, D*0.04);
        v(Material.SEA_LANTERN, D*0.02);
        v(Material.SHROOMLIGHT, D*0.02);
        v(Material.OCHRE_FROGLIGHT, D*0.04);
        v(Material.VERDANT_FROGLIGHT, D*0.04);
        v(Material.PEARLESCENT_FROGLIGHT, D*0.04);
        v(Material.REDSTONE_LAMP, D*0.015);
        v(Material.CHEST, D*0.008);
        v(Material.TRAPPED_CHEST, D*0.01);
        v(Material.BARREL, D*0.008);
        v(Material.CRAFTING_TABLE, D*0.006);
        v(Material.FURNACE, D*0.007);
        v(Material.SMOKER, D*0.012);
        v(Material.BLAST_FURNACE, D*0.015);
        v(Material.LOOM, D*0.008);
        v(Material.CARTOGRAPHY_TABLE, D*0.008);
        v(Material.FLETCHING_TABLE, D*0.008);
        v(Material.SMITHING_TABLE, D*0.008);
        v(Material.STONECUTTER, D*0.008);
        v(Material.GRINDSTONE, D*0.015);
        v(Material.COMPOSTER, D*0.006);
        v(Material.LECTERN, D*0.012);
        v(Material.JUKEBOX, D*0.06);
        v(Material.NOTE_BLOCK, D*0.007);
        v(Material.IRON_DOOR, D*0.15);
        v(Material.IRON_TRAPDOOR, D*0.15);

        // MINERALI
        v(Material.COAL, D*0.008);
        v(Material.COAL_ORE, D*0.01);
        v(Material.DEEPSLATE_COAL_ORE, D*0.011);
        v(Material.COAL_BLOCK, D*0.072);
        v(Material.IRON_INGOT, D*0.05);
        v(Material.RAW_IRON, D*0.045);
        v(Material.IRON_ORE, D*0.06);
        v(Material.DEEPSLATE_IRON_ORE, D*0.065);
        v(Material.IRON_NUGGET, D*0.0055);
        v(Material.IRON_BLOCK, D*0.45);
        v(Material.GOLD_INGOT, D*0.1);
        v(Material.RAW_GOLD, D*0.09);
        v(Material.GOLD_ORE, D*0.12);
        v(Material.DEEPSLATE_GOLD_ORE, D*0.13);
        v(Material.GOLD_NUGGET, D*0.011);
        v(Material.GOLD_BLOCK, D*0.9);
        v(Material.DIAMOND, D);
        v(Material.DIAMOND_ORE, D*1.2);
        v(Material.DEEPSLATE_DIAMOND_ORE, D*1.3);
        v(Material.DIAMOND_BLOCK, D*9);
        v(Material.EMERALD, D*0.6);
        v(Material.EMERALD_ORE, D*0.72);
        v(Material.DEEPSLATE_EMERALD_ORE, D*0.78);
        v(Material.EMERALD_BLOCK, D*5.4);
        v(Material.LAPIS_LAZULI, D*0.03);
        v(Material.LAPIS_ORE, D*0.036);
        v(Material.DEEPSLATE_LAPIS_ORE, D*0.039);
        v(Material.LAPIS_BLOCK, D*0.27);
        v(Material.REDSTONE, D*0.02);
        v(Material.REDSTONE_ORE, D*0.024);
        v(Material.DEEPSLATE_REDSTONE_ORE, D*0.026);
        v(Material.REDSTONE_BLOCK, D*0.18);
        v(Material.QUARTZ, D*0.025);
        v(Material.NETHER_QUARTZ_ORE, D*0.03);
        v(Material.QUARTZ_BLOCK, D*0.1);
        v(Material.QUARTZ_BRICKS, D*0.11);
        v(Material.QUARTZ_PILLAR, D*0.11);
        v(Material.CHISELED_QUARTZ_BLOCK, D*0.11);
        v(Material.SMOOTH_QUARTZ, D*0.1);
        v(Material.QUARTZ_STAIRS, D*0.06);
        v(Material.QUARTZ_SLAB, D*0.03);
        v(Material.SMOOTH_QUARTZ_STAIRS, D*0.06);
        v(Material.SMOOTH_QUARTZ_SLAB, D*0.03);
        v(Material.COPPER_INGOT, D*0.015);
        v(Material.RAW_COPPER, D*0.013);
        v(Material.COPPER_ORE, D*0.018);
        v(Material.DEEPSLATE_COPPER_ORE, D*0.02);
        v(Material.COPPER_BLOCK, D*0.135);
        v(Material.AMETHYST_SHARD, D*0.04);
        v(Material.AMETHYST_BLOCK, D*0.16);
        v(Material.NETHER_GOLD_ORE, D*0.08);
        // Netherite - solo vendita
        v(Material.NETHERITE_INGOT, -1, D*8);
        v(Material.NETHERITE_SCRAP, -1, D*2);
        v(Material.ANCIENT_DEBRIS, -1, D*4);
        v(Material.NETHERITE_BLOCK, -1, D*72);

        // NATURA
        v(Material.SEAGRASS, D*0.002);
        v(Material.TALL_SEAGRASS, D*0.003);
        v(Material.KELP, D*0.0015);
        v(Material.DRIED_KELP_BLOCK, D*0.008);
        v(Material.DRIED_KELP, D*0.001);
        v(Material.SUGAR_CANE, D*0.003);
        v(Material.CACTUS, D*0.004);
        v(Material.PUMPKIN, D*0.005);
        v(Material.CARVED_PUMPKIN, D*0.006);
        v(Material.JACK_O_LANTERN, D*0.008);
        v(Material.MELON_SLICE, D*0.0003);
        v(Material.VINE, D*0.003);
        v(Material.LILY_PAD, D*0.005);
        v(Material.DANDELION, D*0.002);
        v(Material.POPPY, D*0.002);
        v(Material.BLUE_ORCHID, D*0.004);
        v(Material.ALLIUM, D*0.004);
        v(Material.AZURE_BLUET, D*0.003);
        v(Material.RED_TULIP, D*0.003);
        v(Material.ORANGE_TULIP, D*0.003);
        v(Material.WHITE_TULIP, D*0.003);
        v(Material.PINK_TULIP, D*0.003);
        v(Material.OXEYE_DAISY, D*0.003);
        v(Material.CORNFLOWER, D*0.003);
        v(Material.LILY_OF_THE_VALLEY, D*0.004);
        v(Material.WITHER_ROSE, D*0.04);
        v(Material.SUNFLOWER, D*0.005);
        v(Material.LILAC, D*0.005);
        v(Material.ROSE_BUSH, D*0.005);
        v(Material.PEONY, D*0.005);
        v(Material.TALL_GRASS, D*0.001);
        v(Material.LARGE_FERN, D*0.0015);
        v(Material.FERN, D*0.001);
        v(Material.DEAD_BUSH, D*0.001);
        v(Material.RED_MUSHROOM, D*0.003);
        v(Material.BROWN_MUSHROOM, D*0.003);
        v(Material.MUSHROOM_STEM, D*0.003);
        v(Material.RED_MUSHROOM_BLOCK, D*0.003);
        v(Material.BROWN_MUSHROOM_BLOCK, D*0.003);
        v(Material.SPORE_BLOSSOM, D*0.012);
        v(Material.AZALEA, D*0.006);
        v(Material.FLOWERING_AZALEA, D*0.009);
        v(Material.AZALEA_LEAVES, D*0.003);
        v(Material.FLOWERING_AZALEA_LEAVES, D*0.004);
        v(Material.MOSS_BLOCK, D*0.005);
        v(Material.MOSS_CARPET, D*0.002);
        v(Material.BIG_DRIPLEAF, D*0.01);
        v(Material.SMALL_DRIPLEAF, D*0.007);
        v(Material.HANGING_ROOTS, D*0.003);
        v(Material.GLOW_LICHEN, D*0.004);
        v(Material.SWEET_BERRIES, D*0.003);
        v(Material.GLOW_BERRIES, D*0.004);
        v(Material.COCOA_BEANS, D*0.005);
        v(Material.WHEAT, D*0.003);
        v(Material.WHEAT_SEEDS, D*0.001);
        v(Material.POTATO, D*0.002);
        v(Material.CARROT, D*0.002);
        v(Material.BEETROOT, D*0.0015);
        v(Material.BEETROOT_SEEDS, D*0.001);
        v(Material.PUMPKIN_SEEDS, D*0.0015);
        v(Material.MELON_SEEDS, D*0.0015);
        v(Material.APPLE, D*0.005);
        v(Material.TORCHFLOWER, D*0.02);
        v(Material.TORCHFLOWER_SEEDS, D*0.005);
        v(Material.PITCHER_PLANT, D*0.025);
        v(Material.PITCHER_POD, D*0.006);
        v(Material.PINK_PETALS, D*0.004);
        v(Material.STRING, D*0.006);
        v(Material.FEATHER, D*0.005);
        v(Material.LEATHER, D*0.01);
        v(Material.INK_SAC, D*0.008);
        v(Material.GLOW_INK_SAC, D*0.016);
        v(Material.SLIME_BALL, D*0.02);
        v(Material.MAGMA_CREAM, D*0.03);
        v(Material.SPIDER_EYE, D*0.009);
        v(Material.FERMENTED_SPIDER_EYE, D*0.016);
        v(Material.GUNPOWDER, D*0.012);
        v(Material.BONE, D*0.006);
        v(Material.BONE_MEAL, D*0.008);
        v(Material.BONE_BLOCK, D*0.072);
        v(Material.ROTTEN_FLESH, -1, D*0.001);
        v(Material.FLINT, D*0.003);
        v(Material.ARROW, D*0.003);
        v(Material.HONEYCOMB, D*0.009);
        v(Material.HONEYCOMB_BLOCK, D*0.036);
        v(Material.HONEY_BLOCK, D*0.03);
        v(Material.HONEY_BOTTLE, D*0.008);
        v(Material.EGG, D*0.002);
        v(Material.COD, D*0.003);
        v(Material.SALMON, D*0.004);
        v(Material.TROPICAL_FISH, D*0.005);
        v(Material.PUFFERFISH, D*0.006);
        v(Material.PHANTOM_MEMBRANE, D*0.03);
        v(Material.TURTLE_SCUTE, D*0.05);
        v(Material.NAUTILUS_SHELL, D*0.03);
        v(Material.RABBIT_HIDE, D*0.006);
        v(Material.RABBIT_FOOT, D*0.018);
        v(Material.CLAY_BALL, D*0.0015);
        v(Material.SNOWBALL, D*0.0005);
        v(Material.BREEZE_ROD, D*0.08);
        v(Material.ARMADILLO_SCUTE, D*0.05);
        v(Material.PAPER, D*0.002);
        v(Material.BOOK, D*0.006);
        v(Material.BUCKET, D*0.02);
        v(Material.WATER_BUCKET, D*0.024);
        v(Material.LAVA_BUCKET, D*0.032);
        v(Material.MILK_BUCKET, D*0.015);
        v(Material.SADDLE, D*0.5);
        v(Material.LEAD, D*0.009);
        v(Material.NAME_TAG, D*0.03);
        v(Material.BRUSH, D*0.01);
        v(Material.SPYGLASS, D*0.03);
        v(Material.GOAT_HORN, D*0.03);

        // NETHER
        v(Material.NETHERRACK, D*0.003);
        v(Material.NETHER_BRICK, D*0.005);
        v(Material.NETHER_BRICKS, D*0.06);
        v(Material.RED_NETHER_BRICKS, D*0.072);
        v(Material.CRACKED_NETHER_BRICKS, D*0.065);
        v(Material.CHISELED_NETHER_BRICKS, D*0.072);
        v(Material.NETHER_BRICK_STAIRS, D*0.036);
        v(Material.NETHER_BRICK_SLAB, D*0.018);
        v(Material.NETHER_BRICK_FENCE, D*0.03);
        v(Material.NETHER_BRICK_WALL, D*0.03);
        v(Material.RED_NETHER_BRICK_STAIRS, D*0.0432);
        v(Material.RED_NETHER_BRICK_SLAB, D*0.0216);
        v(Material.RED_NETHER_BRICK_WALL, D*0.036);
        v(Material.NETHER_WART, D*0.015);
        v(Material.NETHER_WART_BLOCK, D*0.012);
        v(Material.BLAZE_ROD, D*0.05);
        v(Material.BLAZE_POWDER, D*0.03);
        v(Material.GHAST_TEAR, D*0.08);
        v(Material.SOUL_SAND, D*0.008);
        v(Material.SOUL_SOIL, D*0.006);
        v(Material.GLOWSTONE_DUST, D*0.02);
        v(Material.WITHER_SKELETON_SKULL, D*0.2);
        v(Material.SKELETON_SKULL, -1, D*0.04);
        v(Material.ZOMBIE_HEAD, -1, D*0.03);
        v(Material.CREEPER_HEAD, -1, D*0.03);
        v(Material.PLAYER_HEAD, -1, D*0.02);
        v(Material.PIGLIN_HEAD, -1, D*0.03);
        v(Material.CRIMSON_STEM, D*0.005);
        v(Material.WARPED_STEM, D*0.005);
        v(Material.STRIPPED_CRIMSON_STEM, D*0.0045);
        v(Material.STRIPPED_WARPED_STEM, D*0.0045);
        v(Material.CRIMSON_HYPHAE, D*0.0045);
        v(Material.WARPED_HYPHAE, D*0.0045);
        v(Material.CRIMSON_PLANKS, D*0.004);
        v(Material.WARPED_PLANKS, D*0.004);
        v(Material.CRIMSON_STAIRS, D*0.0024);
        v(Material.WARPED_STAIRS, D*0.0024);
        v(Material.CRIMSON_SLAB, D*0.0012);
        v(Material.WARPED_SLAB, D*0.0012);
        v(Material.CRIMSON_FENCE, D*0.002);
        v(Material.WARPED_FENCE, D*0.002);
        v(Material.CRIMSON_FENCE_GATE, D*0.0032);
        v(Material.WARPED_FENCE_GATE, D*0.0032);
        v(Material.CRIMSON_DOOR, D*0.0032);
        v(Material.WARPED_DOOR, D*0.0032);
        v(Material.CRIMSON_TRAPDOOR, D*0.0032);
        v(Material.WARPED_TRAPDOOR, D*0.0032);
        v(Material.CRIMSON_PRESSURE_PLATE, D*0.0016);
        v(Material.WARPED_PRESSURE_PLATE, D*0.0016);
        v(Material.CRIMSON_BUTTON, D*0.0012);
        v(Material.WARPED_BUTTON, D*0.0012);
        v(Material.CRIMSON_SIGN, D*0.002);
        v(Material.WARPED_SIGN, D*0.002);
        v(Material.CRIMSON_NYLIUM, D*0.005);
        v(Material.WARPED_NYLIUM, D*0.005);
        v(Material.SHROOMLIGHT, D*0.02);
        v(Material.MAGMA_BLOCK, D*0.009);
        v(Material.WEEPING_VINES, D*0.003);
        v(Material.TWISTING_VINES, D*0.003);
        v(Material.CRIMSON_ROOTS, D*0.002);
        v(Material.WARPED_ROOTS, D*0.002);
        v(Material.CRIMSON_FUNGUS, D*0.003);
        v(Material.WARPED_FUNGUS, D*0.003);
        v(Material.WARPED_WART_BLOCK, D*0.012);
        v(Material.GOLD_NUGGET, D*0.011);
        v(Material.FIRE_CHARGE, D*0.006);
        v(Material.BASALT, D*0.003);
        v(Material.SMOOTH_BASALT, D*0.004);
        v(Material.POLISHED_BASALT, D*0.004);
        v(Material.BLACKSTONE, D*0.004);
        v(Material.GILDED_BLACKSTONE, D*0.008);

        // END
        v(Material.END_STONE, D*0.015);
        v(Material.END_STONE_BRICKS, D*0.018);
        v(Material.END_STONE_BRICK_STAIRS, D*0.011);
        v(Material.END_STONE_BRICK_SLAB, D*0.0055);
        v(Material.END_STONE_BRICK_WALL, D*0.009);
        v(Material.PURPUR_BLOCK, D*0.02);
        v(Material.PURPUR_PILLAR, D*0.022);
        v(Material.PURPUR_STAIRS, D*0.012);
        v(Material.PURPUR_SLAB, D*0.006);
        v(Material.ENDER_PEARL, D*0.05);
        v(Material.ENDER_EYE, D*0.1);
        v(Material.DRAGON_BREATH, D*0.15);
        v(Material.SHULKER_SHELL, D*0.2);
        v(Material.CHORUS_FRUIT, D*0.02);
        v(Material.CHORUS_FLOWER, D*0.04);
        v(Material.POPPED_CHORUS_FRUIT, D*0.025);
        v(Material.CHORUS_PLANT, D*0.02);
        v(Material.END_ROD, D*0.012);
        v(Material.END_CRYSTAL, D*25);
        v(Material.ECHO_SHARD, D*25);
        v(Material.ENDER_CHEST, D*20);
        v(Material.SCULK, D*0.012);
        v(Material.SCULK_SENSOR, D*0.06);
        v(Material.CALIBRATED_SCULK_SENSOR, D*0.12);
        v(Material.SCULK_CATALYST, D*0.12);
        v(Material.SCULK_SHRIEKER, D*0.18);
        v(Material.SCULK_VEIN, D*0.008);

        // REDSTONE
        v(Material.REDSTONE, D*0.02);
        v(Material.REDSTONE_BLOCK, D*0.18);
        v(Material.REDSTONE_TORCH, D*0.003);
        v(Material.REDSTONE_LAMP, D*0.015);
        v(Material.REPEATER, D*0.006);
        v(Material.COMPARATOR, D*0.009);
        v(Material.OBSERVER, D*0.012);
        v(Material.PISTON, D*0.012);
        v(Material.STICKY_PISTON, D*0.018);
        v(Material.SLIME_BLOCK, D*0.02);
        v(Material.HONEY_BLOCK, D*0.03);
        v(Material.DISPENSER, D*0.009);
        v(Material.DROPPER, D*0.006);
        v(Material.HOPPER, D*0.024);
        v(Material.LEVER, D*0.002);
        v(Material.STONE_BUTTON, D*0.002);
        v(Material.STONE_PRESSURE_PLATE, D*0.003);
        v(Material.OAK_BUTTON, D*0.002);
        v(Material.OAK_PRESSURE_PLATE, D*0.003);
        v(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, D*0.05);
        v(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, D*0.08);
        v(Material.TARGET, D*0.006);
        v(Material.DAYLIGHT_DETECTOR, D*0.009);
        v(Material.TRIPWIRE_HOOK, D*0.003);
        v(Material.TNT, D*0.04);
        v(Material.MINECART, D*0.015);
        v(Material.CHEST_MINECART, D*0.02);
        v(Material.HOPPER_MINECART, D*0.025);
        v(Material.FURNACE_MINECART, D*0.018);
        v(Material.RAIL, D*0.003);
        v(Material.POWERED_RAIL, D*0.008);
        v(Material.DETECTOR_RAIL, D*0.006);
        v(Material.ACTIVATOR_RAIL, D*0.006);
        v(Material.COMPASS, D*0.012);
        v(Material.CLOCK, D*0.012);
        v(Material.FIREWORK_ROCKET, D*0.003);
        v(Material.FIREWORK_STAR, D*0.005);
        v(Material.RESPAWN_ANCHOR, D*0.15);
        v(Material.LODESTONE, D*0.08);
        v(Material.LIGHTNING_ROD, D*0.05);
        v(Material.SCULK_SENSOR, D*0.06);
        v(Material.CALIBRATED_SCULK_SENSOR, D*0.12);

        // STRUMENTI - solo vendita
        v(Material.WOODEN_SWORD, -1, D*0.002); v(Material.STONE_SWORD, -1, D*0.004);
        v(Material.IRON_SWORD, -1, D*0.04); v(Material.GOLDEN_SWORD, -1, D*0.025);
        v(Material.DIAMOND_SWORD, -1, D*0.4); v(Material.NETHERITE_SWORD, -1, D*1.6);
        v(Material.WOODEN_PICKAXE, -1, D*0.002); v(Material.STONE_PICKAXE, -1, D*0.004);
        v(Material.IRON_PICKAXE, -1, D*0.04); v(Material.GOLDEN_PICKAXE, -1, D*0.025);
        v(Material.DIAMOND_PICKAXE, -1, D*0.4); v(Material.NETHERITE_PICKAXE, -1, D*1.6);
        v(Material.WOODEN_AXE, -1, D*0.002); v(Material.STONE_AXE, -1, D*0.004);
        v(Material.IRON_AXE, -1, D*0.04); v(Material.GOLDEN_AXE, -1, D*0.025);
        v(Material.DIAMOND_AXE, -1, D*0.4); v(Material.NETHERITE_AXE, -1, D*1.6);
        v(Material.WOODEN_SHOVEL, -1, D*0.002); v(Material.STONE_SHOVEL, -1, D*0.004);
        v(Material.IRON_SHOVEL, -1, D*0.04); v(Material.GOLDEN_SHOVEL, -1, D*0.025);
        v(Material.DIAMOND_SHOVEL, -1, D*0.4); v(Material.NETHERITE_SHOVEL, -1, D*1.6);
        v(Material.WOODEN_HOE, -1, D*0.002); v(Material.STONE_HOE, -1, D*0.004);
        v(Material.IRON_HOE, -1, D*0.04); v(Material.GOLDEN_HOE, -1, D*0.025);
        v(Material.DIAMOND_HOE, -1, D*0.4); v(Material.NETHERITE_HOE, -1, D*1.6);
        v(Material.BOW, -1, D*0.03); v(Material.CROSSBOW, -1, D*0.05);
        v(Material.SHIELD, -1, D*0.06); v(Material.FISHING_ROD, -1, D*0.015);
        v(Material.SHEARS, -1, D*0.025); v(Material.FLINT_AND_STEEL, -1, D*0.012);
        v(Material.TRIDENT, -1, D*100);

        // ARMATURE - solo vendita
        v(Material.LEATHER_HELMET, -1, D*0.005); v(Material.LEATHER_CHESTPLATE, -1, D*0.008);
        v(Material.LEATHER_LEGGINGS, -1, D*0.007); v(Material.LEATHER_BOOTS, -1, D*0.005);
        v(Material.IRON_HELMET, -1, D*0.05); v(Material.IRON_CHESTPLATE, -1, D*0.08);
        v(Material.IRON_LEGGINGS, -1, D*0.07); v(Material.IRON_BOOTS, -1, D*0.05);
        v(Material.GOLDEN_HELMET, -1, D*0.03); v(Material.GOLDEN_CHESTPLATE, -1, D*0.05);
        v(Material.GOLDEN_LEGGINGS, -1, D*0.045); v(Material.GOLDEN_BOOTS, -1, D*0.03);
        v(Material.CHAINMAIL_HELMET, -1, D*0.035); v(Material.CHAINMAIL_CHESTPLATE, -1, D*0.06);
        v(Material.CHAINMAIL_LEGGINGS, -1, D*0.05); v(Material.CHAINMAIL_BOOTS, -1, D*0.035);
        v(Material.DIAMOND_HELMET, -1, D*0.5); v(Material.DIAMOND_CHESTPLATE, -1, D*0.8);
        v(Material.DIAMOND_LEGGINGS, -1, D*0.7); v(Material.DIAMOND_BOOTS, -1, D*0.5);
        v(Material.NETHERITE_HELMET, -1, D*2); v(Material.NETHERITE_CHESTPLATE, -1, D*3.2);
        v(Material.NETHERITE_LEGGINGS, -1, D*2.8); v(Material.NETHERITE_BOOTS, -1, D*2);
        v(Material.TURTLE_HELMET, -1, D*0.15);
        v(Material.WOLF_ARMOR, -1, D*0.2);

        // CIBO - solo vendita
        v(Material.BREAD, -1, D*0.002); v(Material.BEEF, -1, D*0.0015);
        v(Material.COOKED_BEEF, -1, D*0.004); v(Material.CHICKEN, -1, D*0.001);
        v(Material.COOKED_CHICKEN, -1, D*0.003); v(Material.PORKCHOP, -1, D*0.0015);
        v(Material.COOKED_PORKCHOP, -1, D*0.004); v(Material.MUTTON, -1, D*0.0013);
        v(Material.COOKED_MUTTON, -1, D*0.0035); v(Material.RABBIT, -1, D*0.001);
        v(Material.COOKED_RABBIT, -1, D*0.003); v(Material.COOKED_COD, -1, D*0.0025);
        v(Material.COOKED_SALMON, -1, D*0.003); v(Material.BAKED_POTATO, -1, D*0.002);
        v(Material.MUSHROOM_STEW, -1, D*0.005); v(Material.RABBIT_STEW, -1, D*0.007);
        v(Material.BEETROOT_SOUP, -1, D*0.005); v(Material.PUMPKIN_PIE, -1, D*0.005);
        v(Material.CAKE, -1, D*0.01); v(Material.COOKIE, -1, D*0.001);
        v(Material.GOLDEN_APPLE, -1, D*0.08); v(Material.ENCHANTED_GOLDEN_APPLE, -1, D*4);
        v(Material.GOLDEN_CARROT, -1, D*0.04); v(Material.HONEY_BOTTLE, D*0.008);

        // POZIONI - solo vendita
        v(Material.POTION, -1, D*0.016); v(Material.SPLASH_POTION, -1, D*0.02);
        v(Material.LINGERING_POTION, -1, D*0.028); v(Material.EXPERIENCE_BOTTLE, -1, D*0.04);

        // LIBRI - solo vendita
        v(Material.ENCHANTED_BOOK, -1, D*0.08); v(Material.KNOWLEDGE_BOOK, -1, D*0.4);
        v(Material.WRITTEN_BOOK, -1, D*0.004); v(Material.WRITABLE_BOOK, D*0.006);

        // DISCHI - solo vendita
        v(Material.MUSIC_DISC_13, -1, D*0.24); v(Material.MUSIC_DISC_CAT, -1, D*0.24);
        v(Material.MUSIC_DISC_BLOCKS, -1, D*0.24); v(Material.MUSIC_DISC_CHIRP, -1, D*0.24);
        v(Material.MUSIC_DISC_FAR, -1, D*0.24); v(Material.MUSIC_DISC_MALL, -1, D*0.24);
        v(Material.MUSIC_DISC_MELLOHI, -1, D*0.24); v(Material.MUSIC_DISC_STAL, -1, D*0.24);
        v(Material.MUSIC_DISC_STRAD, -1, D*0.24); v(Material.MUSIC_DISC_WARD, -1, D*0.24);
        v(Material.MUSIC_DISC_11, -1, D*0.4); v(Material.MUSIC_DISC_WAIT, -1, D*0.24);
        v(Material.MUSIC_DISC_OTHERSIDE, -1, D*0.4); v(Material.MUSIC_DISC_5, -1, D*0.8);
        v(Material.MUSIC_DISC_PIGSTEP, -1, D*0.64); v(Material.MUSIC_DISC_RELIC, -1, D*0.4);
        v(Material.MUSIC_DISC_CREATOR, -1, D*0.48); v(Material.MUSIC_DISC_PRECIPICE, -1, D*0.48);
        v(Material.DISC_FRAGMENT_5, -1, D*0.16);

        // RARI
        v(Material.TOTEM_OF_UNDYING, D*400);
        v(Material.ELYTRA, D*800);
        v(Material.HEART_OF_THE_SEA, D*160);
        v(Material.NETHER_STAR, D*240);
        v(Material.BEACON, -1, D*200);
        v(Material.CONDUIT, -1, D*80);
        v(Material.RECOVERY_COMPASS, D*15);
        v(Material.ECHO_SHARD, D*25);
        v(Material.DRAGON_EGG, -1, D*1000);
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
