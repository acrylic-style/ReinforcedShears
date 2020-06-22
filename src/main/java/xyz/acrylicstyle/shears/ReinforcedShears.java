package xyz.acrylicstyle.shears;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ReinforcedShears extends JavaPlugin implements Listener {
    public static NamespacedKey u4 = null;
    public static NamespacedKey u16 = null;
    public static NamespacedKey u64 = null;
    public static NamespacedKey u256 = null;
    public static NamespacedKey u1024 = null;
    public static NamespacedKey u4096 = null;
    public static NamespacedKey u16384 = null;
    public static NamespacedKey diamond_shears = null;

    public static ItemStack ui4 = null;
    public static ItemStack ui16 = null;
    public static ItemStack ui64 = null;
    public static ItemStack ui256 = null;
    public static ItemStack ui1024 = null;
    public static ItemStack ui4096 = null;
    public static ItemStack ui16384 = null;
    public static ItemStack diamond_shears_item = null;

    public static ItemStack getItemStack(int level) {
        ItemStack item = new ItemStack(Material.SHEARS);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, level, true);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        u4 = new NamespacedKey(this, "u4");
        u16 = new NamespacedKey(this, "u16");
        u64 = new NamespacedKey(this, "u64");
        u256 = new NamespacedKey(this, "u256");
        u1024 = new NamespacedKey(this, "u1024");
        u4096 = new NamespacedKey(this, "u4096");
        u16384 = new NamespacedKey(this, "u16384");
        diamond_shears = new NamespacedKey(this, "diamond_shears");
        ui4 = getItemStack(4);
        ui16 = getItemStack(16);
        ui64 = getItemStack(64);
        ui256 = getItemStack(256);
        ui1024 = getItemStack(1024);
        ui4096 = getItemStack(4096);
        ui16384 = getItemStack(16384);
        diamond_shears_item = new ItemStack(Material.SHEARS);
        ItemMeta meta = diamond_shears_item.getItemMeta();
        meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.AQUA + "ダイヤのハサミ");
        diamond_shears_item.setItemMeta(meta);
        ShapelessRecipe ur4 = new ShapelessRecipe(u4, ui4);
        ShapelessRecipe ur16 = new ShapelessRecipe(u16, ui16);
        ShapelessRecipe ur64 = new ShapelessRecipe(u64, ui64);
        ShapelessRecipe ur256 = new ShapelessRecipe(u256, ui256);
        ShapelessRecipe ur1024 = new ShapelessRecipe(u1024, ui1024);
        ShapelessRecipe ur4096 = new ShapelessRecipe(u4096, ui4096);
        ShapelessRecipe ur16384 = new ShapelessRecipe(u16384, ui16384);
        ShapedRecipe diamond_shears_recipe = new ShapedRecipe(diamond_shears, diamond_shears_item);
        diamond_shears_recipe.shape(" D ", "DSD", " D ");
        diamond_shears_recipe.setIngredient('D', Material.DIAMOND);
        diamond_shears_recipe.setIngredient('S', new ItemStack(Material.SHEARS));
        ur4.addIngredient(4, new ItemStack(Material.SHEARS));
        ur16.addIngredient(4, ui4);
        ur64.addIngredient(4, ui16);
        ur256.addIngredient(4, ui64);
        ur1024.addIngredient(4, ui256);
        ur4096.addIngredient(4, ui1024);
        ur16384.addIngredient(4, ui4096);
        Bukkit.addRecipe(ur4);
        Bukkit.addRecipe(ur16);
        Bukkit.addRecipe(ur64);
        Bukkit.addRecipe(ur256);
        Bukkit.addRecipe(ur1024);
        Bukkit.addRecipe(ur4096);
        Bukkit.addRecipe(ur16384);
        Bukkit.addRecipe(diamond_shears_recipe);
    }

    @Override
    public void onDisable() {
        Bukkit.removeRecipe(u4);
        Bukkit.removeRecipe(u16);
        Bukkit.removeRecipe(u64);
        Bukkit.removeRecipe(u256);
        Bukkit.removeRecipe(u1024);
        Bukkit.removeRecipe(u4096);
        Bukkit.removeRecipe(u16384);
        Bukkit.removeRecipe(diamond_shears);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.ANVIL
                || e.getInventory().getType() == InventoryType.GRINDSTONE) {
            ItemStack item = e.getCurrentItem();
            if (item == null) return;
            if (item.isSimilar(ui4)
                    || item.isSimilar(ui16)
                    || item.isSimilar(ui64)
                    || item.isSimilar(ui256)
                    || item.isSimilar(ui1024)
                    || item.isSimilar(ui4096)
                    || item.isSimilar(ui16384)) {
                e.setCancelled(true);
            }
        }
    }

    private static final Random random = new Random();

    @EventHandler
    public void onBlockShearEntity(BlockShearEntityEvent e) {
        if (!(e.getEntity() instanceof Sheep)) return;
        Sheep sheep = (Sheep) e.getEntity();
        DyeColor color = sheep.getColor();
        String c = color == null ? "WHITE" : color.name();
        handleShear(e.getTool(), e.getEntity().getLocation(), c);
    }

    @EventHandler
    public void onPlayerShearEntity(PlayerShearEntityEvent e) {
        if (!(e.getEntity() instanceof Sheep)) return;
        Sheep sheep = (Sheep) e.getEntity();
        DyeColor color = sheep.getColor();
        String c = color == null ? "WHITE" : color.name();
        handleShear(e.getItem(), e.getEntity().getLocation(), c);
    }

    public void handleShear(@NotNull ItemStack item, @NotNull Location location, @NotNull String color) {
        if (diamond_shears_item.isSimilar(item)) {
            Item iteme = location.getWorld().spawn(location, Item.class);
            iteme.setItemStack(new ItemStack(Material.valueOf(color + "_WOOL"), random.nextInt(3)));
        }
    }
}
