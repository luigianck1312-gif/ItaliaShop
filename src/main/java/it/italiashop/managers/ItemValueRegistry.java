package it.italiashop.managers;

import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;

public class ItemValueRegistry {

    // buy = prezzo acquisto, sell = buy * 0.6 (auto)
    // buy=-1 = solo vendita, sell impostato manualmente
    private static final Map<Material, double[]> VALUES = new HashMap<>();

    static {
        // BLOCCHI BASE
        v(Material.DIRT, 6700);
        v(Material.GRASS_BLOCK, 7000);
        v(Material.COARSE_DIRT, 7000);
        v(Material.PODZOL, 8000);
        v(Material.ROOTED_DIRT, 9000);
        v(Material.MUD, 8000);
        v(Material.PACKED_MUD, 9000);
        v(Material.MUD_BRICKS, 11500);
        v(Material.MUD_BRICK_STAIRS, 12500);
        v(Material.MUD_BRICK_SLAB, 10500);
        v(Material.MUD_BRICK_WALL, 12000);
        v(Material.SAND, 6000);
        v(Material.RED_SAND, 7000);
        v(Material.GRAVEL, 5500);
        v(Material.CLAY, 8000);
        v(Material.CLAY_BALL, 2200);
        v(Material.ICE, 8000);
        v(Material.PACKED_ICE, 16000);
        v(Material.BLUE_ICE, 40000);
        v(Material.SNOW_BLOCK, 5500);
        v(Material.SNOW, 1400);
        v(Material.OBSIDIAN, 32000);
        v(Material.CRYING_OBSIDIAN, 53500);
        v(Material.COBWEB, 21500);
        v(Material.SPONGE, 80000);
        v(Material.WET_SPONGE, -1, 48000);
        v(Material.DRIPSTONE_BLOCK, 8000);
        v(Material.POINTED_DRIPSTONE, 3200);
        v(Material.MYCELIUM, 10700);
        v(Material.DIRT_PATH, 6700);
        v(Material.REINFORCED_DEEPSLATE, -1, 2000000);
        v(Material.BEDROCK, -1, 5000000);

        // PIETRA
        v(Material.STONE, 6700);
        v(Material.SMOOTH_STONE, 8000);
        v(Material.COBBLESTONE, 5000);
        v(Material.MOSSY_COBBLESTONE, 7000);
        v(Material.STONE_BRICKS, 11000);
        v(Material.MOSSY_STONE_BRICKS, 13000);
        v(Material.CRACKED_STONE_BRICKS, 12000);
        v(Material.CHISELED_STONE_BRICKS, 13000);
        v(Material.STONE_STAIRS, 7500);
        v(Material.STONE_SLAB, 6200);
        v(Material.SMOOTH_STONE_SLAB, 7500);
        v(Material.STONE_BRICK_STAIRS, 12000);
        v(Material.STONE_BRICK_SLAB, 9500);
        v(Material.STONE_BRICK_WALL, 11500);
        v(Material.COBBLESTONE_STAIRS, 5800);
        v(Material.COBBLESTONE_SLAB, 4500);
        v(Material.COBBLESTONE_WALL, 5500);
        v(Material.MOSSY_COBBLESTONE_STAIRS, 7800);
        v(Material.MOSSY_COBBLESTONE_SLAB, 6500);
        v(Material.MOSSY_COBBLESTONE_WALL, 7500);
        v(Material.MOSSY_STONE_BRICK_STAIRS, 13800);
        v(Material.MOSSY_STONE_BRICK_SLAB, 12500);
        v(Material.MOSSY_STONE_BRICK_WALL, 13500);
        v(Material.BRICKS, 11000);
        v(Material.BRICK_STAIRS, 11800);
        v(Material.BRICK_SLAB, 10500);
        v(Material.BRICK_WALL, 11500);
        v(Material.SANDSTONE, 6700);
        v(Material.SMOOTH_SANDSTONE, 8000);
        v(Material.CHISELED_SANDSTONE, 9000);
        v(Material.CUT_SANDSTONE, 8000);
        v(Material.SANDSTONE_STAIRS, 7500);
        v(Material.SANDSTONE_SLAB, 6200);
        v(Material.SANDSTONE_WALL, 7200);
        v(Material.SMOOTH_SANDSTONE_STAIRS, 8800);
        v(Material.SMOOTH_SANDSTONE_SLAB, 7500);
        v(Material.CUT_SANDSTONE_SLAB, 7500);
        v(Material.RED_SANDSTONE, 7000);
        v(Material.SMOOTH_RED_SANDSTONE, 8300);
        v(Material.CHISELED_RED_SANDSTONE, 9300);
        v(Material.CUT_RED_SANDSTONE, 8300);
        v(Material.RED_SANDSTONE_STAIRS, 7800);
        v(Material.RED_SANDSTONE_SLAB, 6500);
        v(Material.RED_SANDSTONE_WALL, 7500);
        v(Material.SMOOTH_RED_SANDSTONE_STAIRS, 9100);
        v(Material.SMOOTH_RED_SANDSTONE_SLAB, 7800);
        v(Material.ANDESITE, 6700);
        v(Material.POLISHED_ANDESITE, 8000);
        v(Material.ANDESITE_STAIRS, 7500);
        v(Material.ANDESITE_SLAB, 6200);
        v(Material.ANDESITE_WALL, 7200);
        v(Material.POLISHED_ANDESITE_STAIRS, 8800);
        v(Material.POLISHED_ANDESITE_SLAB, 7500);
        v(Material.DIORITE, 6700);
        v(Material.POLISHED_DIORITE, 8000);
        v(Material.DIORITE_STAIRS, 7500);
        v(Material.DIORITE_SLAB, 6200);
        v(Material.DIORITE_WALL, 7200);
        v(Material.POLISHED_DIORITE_STAIRS, 8800);
        v(Material.POLISHED_DIORITE_SLAB, 7500);
        v(Material.GRANITE, 6700);
        v(Material.POLISHED_GRANITE, 8000);
        v(Material.GRANITE_STAIRS, 7500);
        v(Material.GRANITE_SLAB, 6200);
        v(Material.GRANITE_WALL, 7200);
        v(Material.POLISHED_GRANITE_STAIRS, 8800);
        v(Material.POLISHED_GRANITE_SLAB, 7500);
        v(Material.DEEPSLATE, 7000);
        v(Material.COBBLED_DEEPSLATE, 6000);
        v(Material.POLISHED_DEEPSLATE, 8300);
        v(Material.DEEPSLATE_BRICKS, 11500);
        v(Material.DEEPSLATE_TILES, 12500);
        v(Material.CHISELED_DEEPSLATE, 13000);
        v(Material.CRACKED_DEEPSLATE_BRICKS, 12000);
        v(Material.CRACKED_DEEPSLATE_TILES, 13000);
        v(Material.COBBLED_DEEPSLATE_STAIRS, 6800);
        v(Material.COBBLED_DEEPSLATE_SLAB, 5500);
        v(Material.COBBLED_DEEPSLATE_WALL, 6500);
        v(Material.POLISHED_DEEPSLATE_STAIRS, 9100);
        v(Material.POLISHED_DEEPSLATE_SLAB, 7800);
        v(Material.POLISHED_DEEPSLATE_WALL, 8800);
        v(Material.DEEPSLATE_BRICK_STAIRS, 12300);
        v(Material.DEEPSLATE_BRICK_SLAB, 11000);
        v(Material.DEEPSLATE_BRICK_WALL, 12000);
        v(Material.DEEPSLATE_TILE_STAIRS, 13300);
        v(Material.DEEPSLATE_TILE_SLAB, 12000);
        v(Material.DEEPSLATE_TILE_WALL, 13000);
        v(Material.TUFF, 6700);
        v(Material.POLISHED_TUFF, 8000);
        v(Material.CHISELED_TUFF, 9000);
        v(Material.TUFF_BRICKS, 11000);
        v(Material.CHISELED_TUFF_BRICKS, 12000);
        v(Material.TUFF_STAIRS, 7500);
        v(Material.TUFF_SLAB, 6200);
        v(Material.TUFF_WALL, 7200);
        v(Material.POLISHED_TUFF_STAIRS, 8800);
        v(Material.POLISHED_TUFF_SLAB, 7500);
        v(Material.POLISHED_TUFF_WALL, 8500);
        v(Material.TUFF_BRICK_STAIRS, 11800);
        v(Material.TUFF_BRICK_SLAB, 10500);
        v(Material.TUFF_BRICK_WALL, 11500);
        v(Material.BASALT, 7000);
        v(Material.SMOOTH_BASALT, 8300);
        v(Material.POLISHED_BASALT, 8300);
        v(Material.CALCITE, 6700);
        v(Material.BLACKSTONE, 7500);
        v(Material.GILDED_BLACKSTONE, 15000);
        v(Material.POLISHED_BLACKSTONE, 8800);
        v(Material.CHISELED_POLISHED_BLACKSTONE, 10000);
        v(Material.POLISHED_BLACKSTONE_BRICKS, 12000);
        v(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, 13000);
        v(Material.BLACKSTONE_STAIRS, 8300);
        v(Material.BLACKSTONE_SLAB, 7000);
        v(Material.BLACKSTONE_WALL, 8000);
        v(Material.POLISHED_BLACKSTONE_STAIRS, 9600);
        v(Material.POLISHED_BLACKSTONE_SLAB, 8300);
        v(Material.POLISHED_BLACKSTONE_WALL, 9300);
        v(Material.POLISHED_BLACKSTONE_BRICK_STAIRS, 12800);
        v(Material.POLISHED_BLACKSTONE_BRICK_SLAB, 11500);
        v(Material.POLISHED_BLACKSTONE_BRICK_WALL, 12500);
        v(Material.POLISHED_BLACKSTONE_BUTTON, 4000);
        v(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, 5300);
        v(Material.PRISMARINE, 15000);
        v(Material.PRISMARINE_BRICKS, 20000);
        v(Material.DARK_PRISMARINE, 25000);
        v(Material.SEA_LANTERN, 30000);
        v(Material.PRISMARINE_STAIRS, 15800);
        v(Material.PRISMARINE_SLAB, 14500);
        v(Material.PRISMARINE_BRICK_STAIRS, 20800);
        v(Material.PRISMARINE_BRICK_SLAB, 19500);
        v(Material.DARK_PRISMARINE_STAIRS, 25800);
        v(Material.DARK_PRISMARINE_SLAB, 24500);
        v(Material.PRISMARINE_SHARD, 5000);
        v(Material.PRISMARINE_CRYSTALS, 8000);

        // LEGNO
        String[] woodTypes = {"OAK","BIRCH","SPRUCE","JUNGLE","ACACIA","DARK_OAK","MANGROVE","CHERRY"};
        for (String w : woodTypes) {
            sv(w+"_LOG", 8000); sv(w+"_WOOD", 8500); sv("STRIPPED_"+w+"_LOG", 8500);
            sv(w+"_PLANKS", 9000); sv(w+"_STAIRS", 10000); sv(w+"_SLAB", 8000);
            sv(w+"_FENCE", 11000); sv(w+"_FENCE_GATE", 11500); sv(w+"_DOOR", 13000);
            sv(w+"_TRAPDOOR", 11500); sv(w+"_PRESSURE_PLATE", 5300); sv(w+"_BUTTON", 4000);
            sv(w+"_SIGN", 6700); sv(w+"_HANGING_SIGN", 8000); sv(w+"_LEAVES", 3200);
            sv(w+"_SAPLING", 5300); sv(w+"_BOAT", 16000); sv(w+"_CHEST_BOAT", 21400);
        }
        v(Material.BAMBOO, 8000); v(Material.BAMBOO_BLOCK, 9000);
        v(Material.STRIPPED_BAMBOO_BLOCK, 8500); v(Material.BAMBOO_PLANKS, 9000);
        v(Material.BAMBOO_MOSAIC, 10000); v(Material.BAMBOO_STAIRS, 10000);
        v(Material.BAMBOO_SLAB, 8000); v(Material.BAMBOO_MOSAIC_STAIRS, 10500);
        v(Material.BAMBOO_MOSAIC_SLAB, 8500); v(Material.BAMBOO_FENCE, 11000);
        v(Material.BAMBOO_FENCE_GATE, 11500); v(Material.BAMBOO_DOOR, 13000);
        v(Material.BAMBOO_TRAPDOOR, 11500); v(Material.BAMBOO_PRESSURE_PLATE, 5300);
        v(Material.BAMBOO_BUTTON, 4000); v(Material.BAMBOO_SIGN, 6700);
        v(Material.BAMBOO_HANGING_SIGN, 8000); v(Material.BAMBOO_RAFT, 16000);
        v(Material.BAMBOO_CHEST_RAFT, 21400);

        // DECORAZIONI COLORI
        String[] colors = {"WHITE","ORANGE","MAGENTA","LIGHT_BLUE","YELLOW","LIME","PINK","GRAY","LIGHT_GRAY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
        for (String c : colors) {
            sv(c+"_WOOL", 13400); sv(c+"_CARPET", 8000); sv(c+"_CONCRETE", 18700);
            sv(c+"_CONCRETE_POWDER", 16000); sv(c+"_TERRACOTTA", 16000);
            sv(c+"_GLAZED_TERRACOTTA", 21400); sv(c+"_STAINED_GLASS", 17400);
            sv(c+"_STAINED_GLASS_PANE", 13400); sv(c+"_BED", 24000);
            sv(c+"_BANNER", 18700); sv(c+"_DYE", 2700); sv(c+"_CANDLE", 10700);
            sv(c+"_SHULKER_BOX", 25000000);
        }
        v(Material.TERRACOTTA, 16000); v(Material.GLASS, 8000); v(Material.GLASS_PANE, 5300);
        v(Material.TINTED_GLASS, 21400); v(Material.CANDLE, 10700);
        v(Material.SHULKER_BOX, 25000000);
        v(Material.FLOWER_POT, 4000); v(Material.ITEM_FRAME, 8000);
        v(Material.GLOW_ITEM_FRAME, 21400); v(Material.PAINTING, 6700);
        v(Material.ARMOR_STAND, 13400); v(Material.BOOKSHELF, 24000);
        v(Material.CHISELED_BOOKSHELF, 32000); v(Material.DECORATED_POT, 16000);
        v(Material.TORCH, 2700); v(Material.SOUL_TORCH, 4000);
        v(Material.LANTERN, 10700); v(Material.SOUL_LANTERN, 13400);
        v(Material.END_ROD, 32000); v(Material.CAMPFIRE, 21400);
        v(Material.SOUL_CAMPFIRE, 26700); v(Material.CHEST, 21400);
        v(Material.TRAPPED_CHEST, 26700); v(Material.BARREL, 21400);
        v(Material.CRAFTING_TABLE, 16000); v(Material.FURNACE, 18700);
        v(Material.SMOKER, 32000); v(Material.BLAST_FURNACE, 40000);
        v(Material.LOOM, 21400); v(Material.CARTOGRAPHY_TABLE, 21400);
        v(Material.FLETCHING_TABLE, 21400); v(Material.SMITHING_TABLE, 21400);
        v(Material.STONECUTTER, 21400); v(Material.GRINDSTONE, 40000);
        v(Material.COMPOSTER, 16000); v(Material.LECTERN, 32000);
        v(Material.JUKEBOX, 160000); v(Material.NOTE_BLOCK, 18700);
        v(Material.IRON_DOOR, 400000); v(Material.IRON_TRAPDOOR, 400000);
        v(Material.OCHRE_FROGLIGHT, 107000); v(Material.VERDANT_FROGLIGHT, 107000);
        v(Material.PEARLESCENT_FROGLIGHT, 107000); v(Material.SHROOMLIGHT, 53500);
        v(Material.GLOWSTONE, 107000); v(Material.REDSTONE_LAMP, 40000);
        v(Material.ANVIL, -1, 800000); v(Material.CHIPPED_ANVIL, -1, 500000);
        v(Material.DAMAGED_ANVIL, -1, 200000);
        v(Material.ENCHANTING_TABLE, -1, 5000000);
        v(Material.BREWING_STAND, -1, 500000);
        v(Material.CAULDRON, -1, 400000);
        v(Material.BEACON, -1, 300000000);
        v(Material.CONDUIT, -1, 100000000);
        v(Material.ENDER_CHEST, 53500000);

        // MINERALI
        v(Material.COAL, 50000); v(Material.COAL_ORE, 60000);
        v(Material.DEEPSLATE_COAL_ORE, 65000); v(Material.COAL_BLOCK, 400000);
        v(Material.CHARCOAL, -1, 40000);
        v(Material.IRON_INGOT, 200000); v(Material.RAW_IRON, 150000);
        v(Material.IRON_ORE, 240000); v(Material.DEEPSLATE_IRON_ORE, 260000);
        v(Material.IRON_NUGGET, 22000); v(Material.IRON_BLOCK, 1800000);
        v(Material.GOLD_INGOT, 400000); v(Material.RAW_GOLD, 300000);
        v(Material.GOLD_ORE, 480000); v(Material.DEEPSLATE_GOLD_ORE, 520000);
        v(Material.GOLD_NUGGET, 44500); v(Material.GOLD_BLOCK, 3500000);
        v(Material.DIAMOND, 1250000); v(Material.DIAMOND_ORE, 1500000);
        v(Material.DEEPSLATE_DIAMOND_ORE, 1650000); v(Material.DIAMOND_BLOCK, 11000000);
        v(Material.EMERALD, 900000); v(Material.EMERALD_ORE, 1080000);
        v(Material.DEEPSLATE_EMERALD_ORE, 1170000); v(Material.EMERALD_BLOCK, 8000000);
        v(Material.LAPIS_LAZULI, 100000); v(Material.LAPIS_ORE, 120000);
        v(Material.DEEPSLATE_LAPIS_ORE, 130000); v(Material.LAPIS_BLOCK, 900000);
        v(Material.REDSTONE, 100000); v(Material.REDSTONE_ORE, 120000);
        v(Material.DEEPSLATE_REDSTONE_ORE, 130000); v(Material.REDSTONE_BLOCK, 800000);
        v(Material.QUARTZ, 200000); v(Material.NETHER_QUARTZ_ORE, 240000);
        v(Material.QUARTZ_BLOCK, 800000); v(Material.QUARTZ_BRICKS, 900000);
        v(Material.QUARTZ_PILLAR, 900000); v(Material.CHISELED_QUARTZ_BLOCK, 900000);
        v(Material.SMOOTH_QUARTZ, 850000); v(Material.QUARTZ_STAIRS, 830000);
        v(Material.QUARTZ_SLAB, 780000); v(Material.SMOOTH_QUARTZ_STAIRS, 880000);
        v(Material.SMOOTH_QUARTZ_SLAB, 830000);
        v(Material.COPPER_INGOT, 150000); v(Material.RAW_COPPER, 120000);
        v(Material.COPPER_ORE, 180000); v(Material.DEEPSLATE_COPPER_ORE, 195000);
        v(Material.COPPER_BLOCK, 1200000);
        v(Material.AMETHYST_SHARD, 200000); v(Material.AMETHYST_BLOCK, 1500000);
        v(Material.AMETHYST_CLUSTER, -1, 200000);
        v(Material.NETHER_GOLD_ORE, 480000);
        // Netherite - solo vendita
        v(Material.NETHERITE_INGOT, -1, 20000000);
        v(Material.NETHERITE_SCRAP, -1, 5000000);
        v(Material.ANCIENT_DEBRIS, -1, 10000000);
        v(Material.NETHERITE_BLOCK, -1, 180000000);

        // NATURA
        v(Material.SEAGRASS, 5300); v(Material.TALL_SEAGRASS, 8000);
        v(Material.KELP, 4000); v(Material.DRIED_KELP_BLOCK, 32000); v(Material.DRIED_KELP, -1, 3200);
        v(Material.SUGAR_CANE, 8000); v(Material.CACTUS, 10700);
        v(Material.PUMPKIN, 13400); v(Material.CARVED_PUMPKIN, 16000); v(Material.JACK_O_LANTERN, 21400);
        v(Material.MELON_SLICE, 800); v(Material.VINE, 8000); v(Material.LILY_PAD, 13400);
        v(Material.DANDELION, 5300); v(Material.POPPY, 5300); v(Material.BLUE_ORCHID, 10700);
        v(Material.ALLIUM, 10700); v(Material.AZURE_BLUET, 8000); v(Material.RED_TULIP, 8000);
        v(Material.ORANGE_TULIP, 8000); v(Material.WHITE_TULIP, 8000); v(Material.PINK_TULIP, 8000);
        v(Material.OXEYE_DAISY, 8000); v(Material.CORNFLOWER, 8000); v(Material.LILY_OF_THE_VALLEY, 10700);
        v(Material.WITHER_ROSE, 107000); v(Material.SUNFLOWER, 13400); v(Material.LILAC, 13400);
        v(Material.ROSE_BUSH, 13400); v(Material.PEONY, 13400);
        v(Material.TALL_GRASS, 2700); v(Material.LARGE_FERN, 4000); v(Material.FERN, 2700);
        v(Material.DEAD_BUSH, 2700);
        v(Material.RED_MUSHROOM, 8000); v(Material.BROWN_MUSHROOM, 8000);
        v(Material.MUSHROOM_STEM, 5300); v(Material.RED_MUSHROOM_BLOCK, 5300); v(Material.BROWN_MUSHROOM_BLOCK, 5300);
        v(Material.SPORE_BLOSSOM, 320000); v(Material.AZALEA, 160000); v(Material.FLOWERING_AZALEA, 240000);
        v(Material.AZALEA_LEAVES, 8000); v(Material.FLOWERING_AZALEA_LEAVES, 10700);
        v(Material.MOSS_BLOCK, 133000); v(Material.MOSS_CARPET, 53000);
        v(Material.BIG_DRIPLEAF, 40000); v(Material.SMALL_DRIPLEAF, 26700);
        v(Material.HANGING_ROOTS, 8000); v(Material.GLOW_LICHEN, 10700);
        v(Material.SWEET_BERRIES, 80000); v(Material.GLOW_BERRIES, 107000); v(Material.COCOA_BEANS, 133000);
        v(Material.WHEAT, 100000); v(Material.WHEAT_SEEDS, 26700);
        v(Material.POTATO, 90000); v(Material.CARROT, 90000);
        v(Material.BEETROOT, 40000); v(Material.BEETROOT_SEEDS, 26700);
        v(Material.PUMPKIN_SEEDS, 40000); v(Material.MELON_SEEDS, 40000);
        v(Material.APPLE, 120000); v(Material.TORCHFLOWER, 533000);
        v(Material.TORCHFLOWER_SEEDS, 133000); v(Material.PITCHER_PLANT, 667000);
        v(Material.PITCHER_POD, 160000); v(Material.PINK_PETALS, 107000);
        v(Material.STRING, 80000); v(Material.FEATHER, 50000); v(Material.LEATHER, 120000);
        v(Material.INK_SAC, 213000); v(Material.GLOW_INK_SAC, 427000);
        v(Material.SLIME_BALL, 300000); v(Material.MAGMA_CREAM, 700000);
        v(Material.SPIDER_EYE, 240000); v(Material.FERMENTED_SPIDER_EYE, 427000);
        v(Material.GUNPOWDER, 200000); v(Material.BONE, 100000); v(Material.BONE_MEAL, 120000);
        v(Material.BONE_BLOCK, 1080000); v(Material.ROTTEN_FLESH, -1, 2700);
        v(Material.FLINT, 8000); v(Material.ARROW, 8000); v(Material.SPECTRAL_ARROW, -1, 16000);
        v(Material.HONEYCOMB, 400000); v(Material.HONEYCOMB_BLOCK, 1600000);
        v(Material.HONEY_BLOCK, 1280000); v(Material.HONEY_BOTTLE, 267000);
        v(Material.EGG, 50000); v(Material.COD, 8000); v(Material.SALMON, 10700);
        v(Material.TROPICAL_FISH, 13400); v(Material.PUFFERFISH, 16000);
        v(Material.PHANTOM_MEMBRANE, 800000); v(Material.TURTLE_SCUTE, 1340000);
        v(Material.NAUTILUS_SHELL, 800000); v(Material.RABBIT_HIDE, 160000);
        v(Material.RABBIT_FOOT, 480000); v(Material.BREEZE_ROD, 2140000);
        v(Material.ARMADILLO_SCUTE, 1340000); v(Material.WIND_CHARGE, -1, 1600000);
        v(Material.PAPER, 5300); v(Material.BOOK, 16000);
        v(Material.BUCKET, 533000); v(Material.WATER_BUCKET, 640000);
        v(Material.LAVA_BUCKET, 853000); v(Material.MILK_BUCKET, 400000);
        v(Material.POWDER_SNOW_BUCKET, -1, 200000);
        v(Material.SADDLE, 13400000); v(Material.LEAD, 240000); v(Material.NAME_TAG, 800000);
        v(Material.BRUSH, 267000); v(Material.SPYGLASS, 800000);
        v(Material.GOAT_HORN, 800000);

        // NETHER
        v(Material.NETHERRACK, 100000); v(Material.NETHER_BRICK, 13400);
        v(Material.NETHER_BRICKS, 400000); v(Material.RED_NETHER_BRICKS, 480000);
        v(Material.CRACKED_NETHER_BRICKS, 440000); v(Material.CHISELED_NETHER_BRICKS, 480000);
        v(Material.NETHER_BRICK_STAIRS, 430000); v(Material.NETHER_BRICK_SLAB, 380000);
        v(Material.NETHER_BRICK_FENCE, 420000); v(Material.NETHER_BRICK_WALL, 420000);
        v(Material.RED_NETHER_BRICK_STAIRS, 510000); v(Material.RED_NETHER_BRICK_SLAB, 460000);
        v(Material.RED_NETHER_BRICK_WALL, 500000);
        v(Material.NETHER_WART, 400000); v(Material.NETHER_WART_BLOCK, 320000);
        v(Material.BLAZE_ROD, 800000); v(Material.BLAZE_POWDER, 600000);
        v(Material.GHAST_TEAR, 2500000); v(Material.MAGMA_CREAM, 700000);
        v(Material.SOUL_SAND, 200000); v(Material.SOUL_SOIL, 160000);
        v(Material.GLOWSTONE_DUST, 300000);
        v(Material.WITHER_SKELETON_SKULL, 5340000);
        v(Material.SKELETON_SKULL, -1, 1000000); v(Material.ZOMBIE_HEAD, -1, 800000);
        v(Material.CREEPER_HEAD, -1, 800000); v(Material.PLAYER_HEAD, -1, 500000);
        v(Material.PIGLIN_HEAD, -1, 800000);
        v(Material.CRIMSON_STEM, 13400); v(Material.WARPED_STEM, 13400);
        v(Material.STRIPPED_CRIMSON_STEM, 12000); v(Material.STRIPPED_WARPED_STEM, 12000);
        v(Material.CRIMSON_HYPHAE, 12000); v(Material.WARPED_HYPHAE, 12000);
        v(Material.CRIMSON_PLANKS, 10700); v(Material.WARPED_PLANKS, 10700);
        v(Material.CRIMSON_STAIRS, 11500); v(Material.WARPED_STAIRS, 11500);
        v(Material.CRIMSON_SLAB, 9600); v(Material.WARPED_SLAB, 9600);
        v(Material.CRIMSON_FENCE, 12500); v(Material.WARPED_FENCE, 12500);
        v(Material.CRIMSON_FENCE_GATE, 13000); v(Material.WARPED_FENCE_GATE, 13000);
        v(Material.CRIMSON_DOOR, 15000); v(Material.WARPED_DOOR, 15000);
        v(Material.CRIMSON_TRAPDOOR, 13500); v(Material.WARPED_TRAPDOOR, 13500);
        v(Material.CRIMSON_PRESSURE_PLATE, 5300); v(Material.WARPED_PRESSURE_PLATE, 5300);
        v(Material.CRIMSON_BUTTON, 4000); v(Material.WARPED_BUTTON, 4000);
        v(Material.CRIMSON_SIGN, 6700); v(Material.WARPED_SIGN, 6700);
        v(Material.CRIMSON_NYLIUM, 13400); v(Material.WARPED_NYLIUM, 13400);
        v(Material.SHROOMLIGHT, 53500); v(Material.MAGMA_BLOCK, 24000);
        v(Material.WEEPING_VINES, 8000); v(Material.TWISTING_VINES, 8000);
        v(Material.CRIMSON_ROOTS, 5300); v(Material.WARPED_ROOTS, 5300);
        v(Material.CRIMSON_FUNGUS, 8000); v(Material.WARPED_FUNGUS, 8000);
        v(Material.WARPED_WART_BLOCK, 320000); v(Material.GOLD_NUGGET, 44500);
        v(Material.FIRE_CHARGE, 16000); v(Material.NETHER_GOLD_ORE, 480000);

        // END
        v(Material.END_STONE, 40000); v(Material.END_STONE_BRICKS, 48000);
        v(Material.END_STONE_BRICK_STAIRS, 50700); v(Material.END_STONE_BRICK_SLAB, 45300);
        v(Material.END_STONE_BRICK_WALL, 49300);
        v(Material.PURPUR_BLOCK, 53500); v(Material.PURPUR_PILLAR, 58700);
        v(Material.PURPUR_STAIRS, 56000); v(Material.PURPUR_SLAB, 50700);
        v(Material.ENDER_PEARL, 500000); v(Material.ENDER_EYE, 2670000);
        v(Material.DRAGON_BREATH, 4000000); v(Material.SHULKER_SHELL, 10000000);
        v(Material.CHORUS_FRUIT, 53500); v(Material.CHORUS_FLOWER, 107000);
        v(Material.POPPED_CHORUS_FRUIT, 66700); v(Material.CHORUS_PLANT, 53500);
        v(Material.END_ROD, 320000); v(Material.END_CRYSTAL, 50000000);
        v(Material.ECHO_SHARD, 66700000); v(Material.ENDER_CHEST, 53500000);
        v(Material.DRAGON_EGG, -1, 500000000);
        v(Material.SCULK, 32000); v(Material.SCULK_SENSOR, 160000);
        v(Material.CALIBRATED_SCULK_SENSOR, 320000); v(Material.SCULK_CATALYST, 320000);
        v(Material.SCULK_SHRIEKER, 480000); v(Material.SCULK_VEIN, 21400);

        // REDSTONE
        v(Material.REDSTONE, 100000); v(Material.REDSTONE_BLOCK, 800000);
        v(Material.REDSTONE_TORCH, 8000); v(Material.REDSTONE_LAMP, 40000);
        v(Material.REPEATER, 300000); v(Material.COMPARATOR, 240000);
        v(Material.OBSERVER, 320000); v(Material.PISTON, 320000); v(Material.STICKY_PISTON, 480000);
        v(Material.SLIME_BLOCK, 533000); v(Material.HONEY_BLOCK, 800000);
        v(Material.DISPENSER, 240000); v(Material.DROPPER, 160000); v(Material.HOPPER, 1500000);
        v(Material.LEVER, 5300); v(Material.STONE_BUTTON, 5300); v(Material.STONE_PRESSURE_PLATE, 8000);
        v(Material.OAK_BUTTON, 4000); v(Material.OAK_PRESSURE_PLATE, 5300);
        v(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, 133000); v(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 213000);
        v(Material.TARGET, 16000); v(Material.DAYLIGHT_DETECTOR, 24000); v(Material.TRIPWIRE_HOOK, 8000);
        v(Material.TNT, 107000); v(Material.MINECART, 400000);
        v(Material.CHEST_MINECART, 533000); v(Material.HOPPER_MINECART, 667000);
        v(Material.FURNACE_MINECART, 480000);
        v(Material.RAIL, 8000); v(Material.POWERED_RAIL, 21400);
        v(Material.DETECTOR_RAIL, 16000); v(Material.ACTIVATOR_RAIL, 16000);
        v(Material.COMPASS, 32000); v(Material.CLOCK, 32000);
        v(Material.FIREWORK_ROCKET, 8000); v(Material.FIREWORK_STAR, 13400);
        v(Material.RESPAWN_ANCHOR, 4000000); v(Material.LODESTONE, 2140000);
        v(Material.LIGHTNING_ROD, 1340000);

        // STRUMENTI - solo vendita
        v(Material.WOODEN_SWORD, -1, 20000); v(Material.STONE_SWORD, -1, 40000);
        v(Material.IRON_SWORD, -1, 400000); v(Material.GOLDEN_SWORD, -1, 250000);
        v(Material.DIAMOND_SWORD, -1, 4000000); v(Material.NETHERITE_SWORD, -1, 16000000);
        v(Material.WOODEN_PICKAXE, -1, 20000); v(Material.STONE_PICKAXE, -1, 40000);
        v(Material.IRON_PICKAXE, -1, 400000); v(Material.GOLDEN_PICKAXE, -1, 250000);
        v(Material.DIAMOND_PICKAXE, -1, 4000000); v(Material.NETHERITE_PICKAXE, -1, 16000000);
        v(Material.WOODEN_AXE, -1, 20000); v(Material.STONE_AXE, -1, 40000);
        v(Material.IRON_AXE, -1, 400000); v(Material.GOLDEN_AXE, -1, 250000);
        v(Material.DIAMOND_AXE, -1, 4000000); v(Material.NETHERITE_AXE, -1, 16000000);
        v(Material.WOODEN_SHOVEL, -1, 20000); v(Material.STONE_SHOVEL, -1, 40000);
        v(Material.IRON_SHOVEL, -1, 400000); v(Material.GOLDEN_SHOVEL, -1, 250000);
        v(Material.DIAMOND_SHOVEL, -1, 4000000); v(Material.NETHERITE_SHOVEL, -1, 16000000);
        v(Material.WOODEN_HOE, -1, 20000); v(Material.STONE_HOE, -1, 40000);
        v(Material.IRON_HOE, -1, 400000); v(Material.GOLDEN_HOE, -1, 250000);
        v(Material.DIAMOND_HOE, -1, 4000000); v(Material.NETHERITE_HOE, -1, 16000000);
        v(Material.BOW, -1, 300000); v(Material.CROSSBOW, -1, 500000);
        v(Material.SHIELD, -1, 600000); v(Material.FISHING_ROD, -1, 150000);
        v(Material.SHEARS, -1, 250000); v(Material.FLINT_AND_STEEL, -1, 120000);
        v(Material.TRIDENT, -1, 150000000);

        // ARMATURE - solo vendita
        v(Material.LEATHER_HELMET, -1, 50000); v(Material.LEATHER_CHESTPLATE, -1, 80000);
        v(Material.LEATHER_LEGGINGS, -1, 70000); v(Material.LEATHER_BOOTS, -1, 50000);
        v(Material.IRON_HELMET, -1, 500000); v(Material.IRON_CHESTPLATE, -1, 800000);
        v(Material.IRON_LEGGINGS, -1, 700000); v(Material.IRON_BOOTS, -1, 500000);
        v(Material.GOLDEN_HELMET, -1, 300000); v(Material.GOLDEN_CHESTPLATE, -1, 500000);
        v(Material.GOLDEN_LEGGINGS, -1, 450000); v(Material.GOLDEN_BOOTS, -1, 300000);
        v(Material.CHAINMAIL_HELMET, -1, 350000); v(Material.CHAINMAIL_CHESTPLATE, -1, 600000);
        v(Material.CHAINMAIL_LEGGINGS, -1, 500000); v(Material.CHAINMAIL_BOOTS, -1, 350000);
        v(Material.DIAMOND_HELMET, -1, 5000000); v(Material.DIAMOND_CHESTPLATE, -1, 8000000);
        v(Material.DIAMOND_LEGGINGS, -1, 7000000); v(Material.DIAMOND_BOOTS, -1, 5000000);
        v(Material.NETHERITE_HELMET, -1, 20000000); v(Material.NETHERITE_CHESTPLATE, -1, 32000000);
        v(Material.NETHERITE_LEGGINGS, -1, 28000000); v(Material.NETHERITE_BOOTS, -1, 20000000);
        v(Material.TURTLE_HELMET, -1, 1500000); v(Material.WOLF_ARMOR, -1, 2000000);

        // CIBO - solo vendita
        v(Material.BREAD, -1, 20000); v(Material.BEEF, -1, 15000); v(Material.COOKED_BEEF, -1, 40000);
        v(Material.CHICKEN, -1, 10000); v(Material.COOKED_CHICKEN, -1, 30000);
        v(Material.PORKCHOP, -1, 15000); v(Material.COOKED_PORKCHOP, -1, 40000);
        v(Material.MUTTON, -1, 13000); v(Material.COOKED_MUTTON, -1, 35000);
        v(Material.RABBIT, -1, 10000); v(Material.COOKED_RABBIT, -1, 30000);
        v(Material.COOKED_COD, -1, 25000); v(Material.COOKED_SALMON, -1, 30000);
        v(Material.BAKED_POTATO, -1, 20000); v(Material.MUSHROOM_STEW, -1, 50000);
        v(Material.RABBIT_STEW, -1, 70000); v(Material.BEETROOT_SOUP, -1, 50000);
        v(Material.PUMPKIN_PIE, -1, 50000); v(Material.CAKE, -1, 100000);
        v(Material.COOKIE, -1, 10000); v(Material.GOLDEN_APPLE, -1, 800000);
        v(Material.ENCHANTED_GOLDEN_APPLE, -1, 40000000);
        v(Material.GOLDEN_CARROT, -1, 400000);

        // POZIONI - solo vendita
        v(Material.POTION, -1, 160000); v(Material.SPLASH_POTION, -1, 200000);
        v(Material.LINGERING_POTION, -1, 280000); v(Material.EXPERIENCE_BOTTLE, -1, 400000);

        // LIBRI - solo vendita
        v(Material.ENCHANTED_BOOK, -1, 800000); v(Material.KNOWLEDGE_BOOK, -1, 4000000);
        v(Material.WRITTEN_BOOK, -1, 40000); v(Material.WRITABLE_BOOK, 16000);

        // DISCHI - solo vendita
        v(Material.MUSIC_DISC_13, -1, 2400000); v(Material.MUSIC_DISC_CAT, -1, 2400000);
        v(Material.MUSIC_DISC_BLOCKS, -1, 2400000); v(Material.MUSIC_DISC_CHIRP, -1, 2400000);
        v(Material.MUSIC_DISC_FAR, -1, 2400000); v(Material.MUSIC_DISC_MALL, -1, 2400000);
        v(Material.MUSIC_DISC_MELLOHI, -1, 2400000); v(Material.MUSIC_DISC_STAL, -1, 2400000);
        v(Material.MUSIC_DISC_STRAD, -1, 2400000); v(Material.MUSIC_DISC_WARD, -1, 2400000);
        v(Material.MUSIC_DISC_11, -1, 4000000); v(Material.MUSIC_DISC_WAIT, -1, 2400000);
        v(Material.MUSIC_DISC_OTHERSIDE, -1, 4000000); v(Material.MUSIC_DISC_5, -1, 8000000);
        v(Material.MUSIC_DISC_PIGSTEP, -1, 6400000); v(Material.MUSIC_DISC_RELIC, -1, 4000000);
        v(Material.MUSIC_DISC_CREATOR, -1, 4800000); v(Material.MUSIC_DISC_PRECIPICE, -1, 4800000);
        v(Material.DISC_FRAGMENT_5, -1, 1600000);

        // RARI
        v(Material.TOTEM_OF_UNDYING, 500000000);
        v(Material.ELYTRA, 1000000000);
        v(Material.HEART_OF_THE_SEA, 200000000);
        v(Material.NETHER_STAR, 300000000);
        v(Material.TRIDENT, 150000000);
        v(Material.END_CRYSTAL, 50000000);
        v(Material.RECOVERY_COMPASS, 40000000);
        v(Material.BEACON, -1, 300000000);
        v(Material.CONDUIT, -1, 100000000);
        v(Material.DRAGON_EGG, -1, 500000000);
    }

    // buy > 0: sell = buy * 0.6
    private static void v(Material mat, double buy) {
        VALUES.put(mat, new double[]{buy, buy * 0.6});
    }

    // buy = -1: solo vendita con sell fisso
    private static void v(Material mat, double buy, double sell) {
        double actualSell = buy > 0 ? buy * 0.6 : sell;
        VALUES.put(mat, new double[]{buy, actualSell});
    }

    private static void sv(String name, double buy) {
        try { v(Material.valueOf(name), buy); } catch (Exception ignored) {}
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
