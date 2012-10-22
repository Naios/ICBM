package icbm;

import icbm.api.ICBM;
import icbm.cart.EChe;
import icbm.daodan.DaoDan;
import icbm.daodan.EDaoDan;
import icbm.daodan.ItDaoDan;
import icbm.daodan.ItTeBieDaoDan;
import icbm.dianqi.ItGenZongQi;
import icbm.dianqi.ItHuoLuanQi;
import icbm.dianqi.ItJieJa;
import icbm.dianqi.ItLeiDaQiang;
import icbm.dianqi.ItLeiShiZhiBiao;
import icbm.dianqi.ItYaoKong;
import icbm.jiqi.BJiQi;
import icbm.jiqi.BYinGanQi;
import icbm.jiqi.EFake;
import icbm.jiqi.IBJiQi;
import icbm.jiqi.TYinGanQi;
import icbm.po.PChuanRanDu;
import icbm.po.PDaDu;
import icbm.po.PDongShang;
import icbm.zhapin.BZhaDan;
import icbm.zhapin.EShouLiuDan;
import icbm.zhapin.EZhaDan;
import icbm.zhapin.EZhaPin;
import icbm.zhapin.IBZhaDan;
import icbm.zhapin.TZhaDan;
import icbm.zhapin.ZhaPin;

import java.io.File;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockRail;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ICommandManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ServerCommandManager;
import net.minecraft.src.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.BasicComponents;
import universalelectricity.UEConfig;
import universalelectricity.UniversalElectricity;
import universalelectricity.implement.UEDamageSource;
import universalelectricity.ore.OreGenBase;
import universalelectricity.ore.OreGenerator;
import universalelectricity.prefab.ItemElectric;
import universalelectricity.prefab.Vector3;
import universalelectricity.recipe.RecipeManager;
import atomicscience.api.BlockRadioactive;
import atomicscience.api.PoisonRadiation;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IDispenserHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ICBM", name = "ICBM", version = ZhuYao.VERSION, dependencies = "after:UniversalElectricity;after:AtomicScience")
@NetworkMod(channels = ZhuYao.CHANNEL, clientSideRequired = true, serverSideRequired = false, packetHandler = ICBMPacketManager.class)

public class ZhuYao
{
	@Instance("ICBM")
	public static ZhuYao instance;
	
	/**
	 * The version of ICBM.
	 */
	public static final String VERSION = "0.6.0";
	
	public static final String CHANNEL = "ICBM";
	
	//Configurations
	public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "UniversalElectricity/ICBM.cfg"));
    
	public static final boolean SPAWN_PARTICLES = UEConfig.getConfigData(CONFIGURATION, "Spawn Particle Effects", true);
	public static final boolean ADVANCED_VISUALS = UEConfig.getConfigData(CONFIGURATION, "Advanced Visual Effects", false);
	public static final boolean ALLOW_LOAD_CHUNKS = UEConfig.getConfigData(CONFIGURATION, "Allow Chunk Loading", true);
	
	@SidedProxy(clientSide = "icbm.ICBMClientProxy", serverSide = "icbm.ICBMCommonProxy")
	public static ICBMCommonProxy proxy;
	
	//BLOCKS
	public static final int ENTITY_ID_PREFIX = 50;
	
	public static final int BLOCK_ID_PREFIX = 3880;
    public static final Block bLiu = new BLiu(UEConfig.getBlockConfigID(CONFIGURATION, "Sulfur Ores", BLOCK_ID_PREFIX-1));
	public static final Block bBuo1LiPan = new BBuo1Li4Pan2(UEConfig.getBlockConfigID(CONFIGURATION, "Glass Pressure Plate", BLOCK_ID_PREFIX+0), 0);
	public static final Block bZha4Dan4 = new BZhaDan(UEConfig.getBlockConfigID(CONFIGURATION, "Explosives", BLOCK_ID_PREFIX+1), 16);
	public static final Block bJiQi = new BJiQi(UEConfig.getBlockConfigID(CONFIGURATION, "Block Machine", BLOCK_ID_PREFIX+3));
	public static Block bFuShe;
	public static final Block bYinGanQi = new BYinGanQi(UEConfig.getBlockConfigID(CONFIGURATION, "Proximity Detector", BLOCK_ID_PREFIX+6), 7);

	//ITEMS
	public static final int ITEM_ID_PREFIX = 3900;
	public static final Item itLiu = new ICBMItem("Sulfur", UEConfig.getItemConfigID(CONFIGURATION, "Sulfur", ITEM_ID_PREFIX-2), 3, CreativeTabs.tabMaterials);
	public static final Item itDu = new ICBMItem("Poison Powder", UEConfig.getItemConfigID(CONFIGURATION, "Poison Powder", ITEM_ID_PREFIX), 0, CreativeTabs.tabMaterials);
	public static final Item itYao = new ItYao("Antidote", UEConfig.getItemConfigID(CONFIGURATION, "Antidote", ITEM_ID_PREFIX+1), 5);
	public static final Item itDaoDan = new ItDaoDan("Missile", UEConfig.getItemConfigID(CONFIGURATION, "Missile", ITEM_ID_PREFIX+2), 32);
	public static final Item itTeBieDaoDan = new ItTeBieDaoDan("Special Missile", UEConfig.getItemConfigID(CONFIGURATION, "Special Missile", ITEM_ID_PREFIX+3), 32);
	
	public static final ItemElectric itJieJa = new ItJieJa("Defuser", UEConfig.getItemConfigID(CONFIGURATION, "Explosive Defuser", ITEM_ID_PREFIX+4), 21);
	public static final ItemElectric itLeiDaQiang = new ItLeiDaQiang("Radar Gun", UEConfig.getItemConfigID(CONFIGURATION, "RadarGun", ITEM_ID_PREFIX+5), 19);
	public static final ItemElectric itYaoKong = new ItYaoKong("Remote", UEConfig.getItemConfigID(CONFIGURATION, "Remote", ITEM_ID_PREFIX+6), 20);
	public static final ItemElectric itLeiSheZhiBiao = new ItLeiShiZhiBiao("Laser Designator", UEConfig.getItemConfigID(CONFIGURATION, "Laser Designator", ITEM_ID_PREFIX+7), 22);
	public static final ItemElectric itHuoLaunQi = new ItHuoLuanQi("Signal Disruptor", UEConfig.getItemConfigID(CONFIGURATION, "Signal Disruptor", ITEM_ID_PREFIX+9), 23);
	public static final ItemElectric itGenZongQi = new ItGenZongQi("Tracker", UEConfig.getItemConfigID(CONFIGURATION, "Tracker", ITEM_ID_PREFIX+11), 0);

	public static final Item itShouLiuDan = new ItShouLiuDan("Grenade", UEConfig.getItemConfigID(CONFIGURATION, "Grenade", ITEM_ID_PREFIX+8), 64);
	public static final Item itZiDan = new ItZiDan("Bullet", UEConfig.getItemConfigID(CONFIGURATION, "Bullet", ITEM_ID_PREFIX+10), 80);
	
	public static final Item itChe = new ItChe(UEConfig.getItemConfigID(CONFIGURATION, "Minecart", ITEM_ID_PREFIX+11), 135);

	public static final Du DU_DU = new Du("Chemical", 1, false);
	public static final Du DU_CHUAN_RAN = new Du("Contagious", 1, true);
	
	public static final UEDamageSource damageSmash = new UEDamageSource("smash", "%1$s got smashed to death!");
		
    public static final OreGenBase liuGenData = new GenLiu("Sulfur Ore", "oreSulfur", new ItemStack(bLiu), 0, 40, 25, 15).enable();
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
    {
		if(!Loader.isModLoaded("UniversalElectricity")) throw new RuntimeException("Universal Electricity not installed!");
		
		UniversalElectricity.versionLock(1, 0, 0, true);
		
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		
		GameRegistry.registerDispenserHandler(
			new IDispenserHandler()
	        {
	            @Override
	            public int dispense(int x, int y, int z, int xVelocity, int zVelocity, World world, ItemStack itemStack, Random random, double entX, double entY, double entZ)
	            {
	            	if(!world.isRemote)
	            	{
		            	if(itemStack.itemID == ZhuYao.itShouLiuDan.shiftedIndex)
		        		{
		        			EShouLiuDan entity = new EShouLiuDan(world, new Vector3(x, y, z), itemStack.getItemDamage());
		        	        entity.setThrowableHeading(xVelocity, 0.10000000149011612D, zVelocity, 1.1F, 6.0F);
		        	        world.spawnEntityInWorld(entity);
		        	        return 1;
		        		}
		            	else if(itemStack.itemID == ZhuYao.itChe.shiftedIndex)
		        		{
		            		entX = (double)x + (xVelocity < 0 ? (double)xVelocity * 0.8D : (double)((float)xVelocity * 1.8F)) + (double)((float)Math.abs(zVelocity) * 0.5F);
		                    entZ = (double)z + (zVelocity < 0 ? (double)zVelocity * 0.8D : (double)((float)zVelocity * 1.8F)) + (double)((float)Math.abs(xVelocity) * 0.5F);

		                    if (BlockRail.isRailBlockAt(world, x + xVelocity, y, z + zVelocity))
		                    {
		                        entY = (double)((float)y + 0.5F);
		                    }
		                    else
		                    {
		                        if (!world.isAirBlock(x + xVelocity, y, z + zVelocity) || !BlockRail.isRailBlockAt(world, x + xVelocity, y - 1, z + zVelocity))
		                        {
		                            return 0;
		                        }

		                        entY = (double)((float)y - 0.5F);
		                    }

		                    EChe var22 = new EChe(world, entX, entY, entZ, itemStack.getItemDamage());
		                    world.spawnEntityInWorld(var22);
		                    world.playAuxSFX(1000, x, y, z, 0);
		                    return 1;
		        		}
	            	}
	        		
	        		return -1;
	            }
	        }
		);
		
		//-- Registering Blocks
		GameRegistry.registerBlock(bLiu);
		GameRegistry.registerBlock(bBuo1LiPan);
		GameRegistry.registerBlock(bZha4Dan4, IBZhaDan.class);
		GameRegistry.registerBlock(bJiQi, IBJiQi.class);
		GameRegistry.registerBlock(bYinGanQi);
		
		if(OreDictionary.getOres("blockRadioactive").size() > 0)
		{
			bFuShe = Block.blocksList[OreDictionary.getOres("blockRadioactive").get(0).itemID];
			System.out.println("Detected radioative block from another mod.");
		}
		
		OreDictionary.registerOre("dustSulfur", itLiu);
		
		OreGenerator.addOre(liuGenData);
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new DaoDanCLCallBack());
   		MinecraftForge.EVENT_BUS.register(this);
   		
   		//Set ICBM API Variables
   		ICBM.explosiveBlock = this.bZha4Dan4;

		this.proxy.preInit();
    }
	
	public class DaoDanCLCallBack implements ForgeChunkManager.LoadingCallback
	{
		@Override
		public void ticketsLoaded(List<Ticket> tickets, World world)
		{
			for (Ticket ticket : tickets)
			{
				if(ticket.getEntity() != null)
				{
					((EDaoDan)ticket.getEntity()).daoDanInit(ticket);
				}
			}
		}
	}
	
	/*
	@ForgeSubscribe
	public void onEEC(EnteringChunk event)
	{
		if(event.entity instanceof EDaoDan)
		{
			((EDaoDan)event.entity).updateLoadChunk(event.oldChunkX, event.oldChunkZ, event.newChunkX, event.newChunkZ);
		}
	}*/
	
	@Init
	public void load(FMLInitializationEvent evt)
    {
		if(bFuShe == null)
		{
			bFuShe = new BlockRadioactive(UEConfig.getBlockConfigID(CONFIGURATION, "Radioactive Block", BLOCK_ID_PREFIX+5), 4, ICBM.BLOCK_TEXTURE_FILE);
			GameRegistry.registerBlock(bFuShe);
		}

		//-- Add Names
		LanguageRegistry.addName(bLiu, "Sulfur Ore");
		
		LanguageRegistry.addName(itLiu, "Sulfur Dust");
		LanguageRegistry.addName(itDu, "Poison Powder");
		
		LanguageRegistry.addName(ZhuYao.itLeiDaQiang, "Radar Gun");
		LanguageRegistry.addName(ZhuYao.itYaoKong, "Remote Detonator");
		LanguageRegistry.addName(ZhuYao.itLeiSheZhiBiao, "Laser Designator");
		LanguageRegistry.addName(ZhuYao.itJieJa, "Explosive Defuser");
		LanguageRegistry.addName(ZhuYao.itGenZongQi, "Tracker");
		LanguageRegistry.addName(ZhuYao.itHuoLaunQi, "Signal Disruptor");
		LanguageRegistry.addName(new ItemStack(ZhuYao.itZiDan, 1, 0), "Conventional Bullet");
		LanguageRegistry.addName(new ItemStack(ZhuYao.itZiDan, 1, 1), "Antimatter Bullet");
				
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 0), "Launcher Platform T1");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 1), "Launcher Platform T2");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 2), "Launcher Platform T3");
		
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 3), "Launcher Control Panel T1");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 4), "Launcher Control Panel T2");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 5), "Launcher Control Panel T3");
		
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 6), "Launcher Support Frame T1");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 7), "Launcher Support Frame T2");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 8), "Launcher Support Frame T3");
		
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 9), "Radar Station");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 10), "EMP Tower");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 11), "Railgun");
		LanguageRegistry.addName(new ItemStack(ZhuYao.bJiQi, 1, 12), "Cruise Launcher");
		
		LanguageRegistry.addName(ZhuYao.itYao, "Antidote");
		
		LanguageRegistry.addName(ZhuYao.bBuo1LiPan, "Glass Pressure Plate");
		
		LanguageRegistry.addName(ZhuYao.bYinGanQi, "Proximity Detector");
		
		for(int i = 0; i < ((ItTeBieDaoDan)ZhuYao.itTeBieDaoDan).names.length; i++)
		{
		    LanguageRegistry.addName(new ItemStack(ZhuYao.itTeBieDaoDan, 1, i), ((ItTeBieDaoDan)ZhuYao.itTeBieDaoDan).names[i]);
		}
		
		//Explosives and missile recipe
		for(int i = 0; i < ZhaPin.MAX_EXPLOSIVE_ID; i++)
		{
			if(i == 0)
			{	
				LanguageRegistry.addName(new ItemStack(ZhuYao.itDaoDan, 1, i), "Conventional Missile");
				LanguageRegistry.addName(new ItemStack(ZhuYao.itShouLiuDan, 1, i), "Conventional Grenade");
				LanguageRegistry.addName(new ItemStack(ZhuYao.itChe, 1, i), "Conventional Minecart");
			}
			else
			{
				LanguageRegistry.addName(new ItemStack(ZhuYao.itDaoDan, 1, i), ZhaPin.list[i].getMing()+" Missile");
			
				if(i < ZhaPin.MAX_TIER_ONE)
				{
					LanguageRegistry.addName(new ItemStack(itShouLiuDan, 1, i), ZhaPin.list[i].getMing()+" Grenade");
				}
				
				if(i < ZhaPin.MAX_TIER_TWO)
				{
					LanguageRegistry.addName(new ItemStack(itChe, 1, i), ZhaPin.list[i].getMing()+" Minecart");
				}
			}
		
			LanguageRegistry.addName(new ItemStack(ZhuYao.bZha4Dan4, 1, i), ZhaPin.list[i].getMing()+" Explosives");
		}
		 
	    //-- Recipes
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itZiDan, 3, 0), new Object [] {"@", "!", "!", '@', Item.diamond, '!', "ingotBronze"});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itZiDan, 1, 1), new Object [] {"@", "!", "!", '@', new ItemStack(ZhuYao.bZha4Dan4, 1, ZhaPin.fanWuSu.getID()), '!', ZhuYao.itZiDan});
		
		//Poison Powder
		RecipeManager.addShapelessRecipe(new ItemStack(itDu), new Object [] {Item.fermentedSpiderEye, Item.rottenFlesh});
		
		//Sulfur
		RecipeManager.addSmelting(new ItemStack(bLiu, 1, 0), new ItemStack(itLiu));
		RecipeManager.addRecipe(new ItemStack(Item.gunpowder, 5), new Object [] {"@@@", "@?@", "@@@", '@', "dustSulfur", '?', Item.coal});
		RecipeManager.addRecipe(new ItemStack(Item.gunpowder, 5), new Object [] {"@@@", "@?@", "@@@", '@', "dustSulfur", '?', new ItemStack(Item.coal, 1, 1)});
		
		//Radar Gun
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itLeiDaQiang), new Object [] {"@#!", " $!", "  !", '@', Block.glass, '!', "ingotSteel", '#', BasicComponents.itemCircuit, '$', Block.button});
		//Remote
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itYaoKong), new Object [] {"?@@", "@#$", "@@@", '@', "ingotSteel", '?', Item.redstone, '#', new ItemStack(BasicComponents.itemCircuit, 1, 1), '$', Block.button});
		//Laser Designator
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itLeiSheZhiBiao), new Object [] {"!  ", " ? ", "  @", '@', ZhuYao.itYaoKong.getUnchargedItemStack(), '?', new ItemStack(BasicComponents.itemCircuit, 1, 2), '!', ZhuYao.itLeiDaQiang.getUnchargedItemStack()});
		//Proximity Detector
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bYinGanQi), new Object [] {" ! ", "!?!", " ! ", '!', BasicComponents.itemSteelPlate, '?', new ItemStack(BasicComponents.itemCircuit, 1, 2)});
		//Signal Disrupter
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itHuoLaunQi), new Object [] {"!", "?", '!', ZhuYao.itYaoKong.getUnchargedItemStack(), '?', ZhuYao.bYinGanQi});
		//Antidote
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itYao, 2), new Object [] {"@@@", "@@@", "@@@", '@', Item.pumpkinSeeds});
		//Defuser
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itJieJa), new Object [] {"?  ", " @ ", "  !", '@', new ItemStack(BasicComponents.itemCircuit, 1, 1), '!', BasicComponents.itemSteelPlate, '?', BasicComponents.blockCopperWire});
		//Missile Launcher Platform
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 0), new Object [] {"! !", "!@!", "!!!", '!', "ingotBronze", '@', BasicComponents.itemSteelPlate});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 1), new Object [] {"! !", "! !", "!@!", '@', new ItemStack(ZhuYao.bJiQi, 1, 0), '!', "ingotSteel"});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 2), new Object [] {"! !", "! !", "!@!", '@', new ItemStack(ZhuYao.bJiQi, 1, 1), '!', BasicComponents.itemSteelPlate});
		//Missile Launcher Computer
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 3), new Object [] {"!!!", "!#!", "!?!", '#', BasicComponents.itemCircuit, '!', Block.glass, '?', BasicComponents.blockCopperWire});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 4), new Object [] {"!$!", "!#!", "!?!", '#', new ItemStack(BasicComponents.itemCircuit, 1, 1), '!', "ingotSteel", '?', BasicComponents.blockCopperWire, '$', new ItemStack(ZhuYao.bJiQi, 1, 3)});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 5), new Object [] {"!$!", "!#!", "!?!", '#', new ItemStack(BasicComponents.itemCircuit, 1, 2), '!', Item.ingotGold, '?', BasicComponents.blockCopperWire, '$', new ItemStack(ZhuYao.bJiQi, 1, 4)});
		//Missile Launcher Frame
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 6), new Object [] {"! !", "!!!", "! !", '!', "ingotBronze"});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 7), new Object [] {"! !", "!@!", "! !", '!', "ingotSteel", '@', new ItemStack(ZhuYao.bJiQi, 1, 6)});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 8), new Object [] {"! !", "!@!", "! !", '!', BasicComponents.itemSteelPlate, '@', new ItemStack(ZhuYao.bJiQi, 1, 7)});
		//Radar Station
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 9), new Object [] {"?@?", " ! ", "!#!", '@', ZhuYao.itLeiDaQiang.getUnchargedItemStack(), '!', "ingotSteel", '#', new ItemStack(BasicComponents.itemCircuit, 1, 2), '?', Item.ingotGold});
		//EMP Tower
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 10), new Object [] {"???", "@!@", "?#?", '?', BasicComponents.itemSteelPlate, '!', new ItemStack(BasicComponents.itemCircuit, 1, 2), '@', BasicComponents.batteryBox, '#', BasicComponents.itemMotor});
		//Railgun
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 11), new Object [] {"?!#", "@@@", '@', BasicComponents.itemSteelPlate, '!', ZhuYao.itLeiDaQiang.getUnchargedItemStack(), '#', Item.diamond, '?', new ItemStack(BasicComponents.itemCircuit, 1, 2)});
		//Cruise Launcher
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 12), new Object [] {"?! ", "@@@", '@', BasicComponents.itemSteelPlate, '!', new ItemStack(ZhuYao.bJiQi, 1, 2), '?', new ItemStack(ZhuYao.bJiQi, 1, 8)});
		//Laser Turret
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bJiQi, 1, 13), new Object [] {"?!#", "@@@", '@', BasicComponents.itemSteelPlate, '!', ZhuYao.itLeiDaQiang.getUnchargedItemStack(), '#', Item.diamond, '?', new ItemStack(BasicComponents.itemCircuit, 1, 1)});
		//Glass Pressure Plate
		RecipeManager.addRecipe(new ItemStack(ZhuYao.bBuo1LiPan, 1, 0), new Object [] {"##", '#', Block.glass});
		//Missiles
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itTeBieDaoDan, 1, 0), new Object [] {" @ ", "@#@", "@?@", '@', "ingotSteel", '?', BasicComponents.itemOilBucket, '#', BasicComponents.itemCircuit});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itTeBieDaoDan, 1, 1), new Object [] {"!", "?", "@", '@', new ItemStack(ZhuYao.itTeBieDaoDan, 1, 0), '?', new ItemStack(ZhuYao.bZha4Dan4, 1, 0), '!', BasicComponents.itemCircuit});
		RecipeManager.addRecipe(new ItemStack(ZhuYao.itTeBieDaoDan, 1, 2), new Object [] {" ! ", " ? ", "!@!", '@', new ItemStack(ZhuYao.itTeBieDaoDan, 1, 0), '?', DaoDan.list[ZhaPin.qunDan.getID()].getItemStack(), '!', new ItemStack(ZhuYao.itDaoDan, 1, 0)});
		
		for(int i = 0; i < ZhaPin.MAX_EXPLOSIVE_ID; i++)
		{
			ZhaPin.list[i].init();
				
	    	//Missile
			RecipeManager.addShapelessRecipe(new ItemStack(ZhuYao.itDaoDan, 1, i), new Object [] {new ItemStack(ZhuYao.itTeBieDaoDan, 1, 0), new ItemStack(ZhuYao.bZha4Dan4, 1, i)}, ZhaPin.list[i].getDaoDanMing(), CONFIGURATION, true);        
	
			if(i < ZhaPin.MAX_TIER_ONE)
			{
				//Grenade
			    RecipeManager.addRecipe(new ItemStack(ZhuYao.itShouLiuDan, 1, i), new Object [] {"?", "@", '@', new ItemStack(ZhuYao.bZha4Dan4, 1, i), '?', Item.silk}, CONFIGURATION, true);
			}
			
			if(i < ZhaPin.MAX_TIER_TWO)
			{
				//Minecart
				RecipeManager.addRecipe(new ItemStack(ZhuYao.itChe, 1, i), new Object [] {"?", "@", '?', new ItemStack(ZhuYao.bZha4Dan4, 1, i), '@', Item.minecartEmpty}, CONFIGURATION, true);
	        }
		}
		
		GameRegistry.registerTileEntity(TZhaDan.class, "ICBMExplosive");
		GameRegistry.registerTileEntity(TYinGanQi.class, "ICBMDetector");
		
		EntityRegistry.registerGlobalEntityID(EZhaDan.class, "ICBMExplosive", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EDaoDan.class, "ICBMMissile", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EZhaPin.class, "ICBMProceduralExplosion", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EFeiBlock.class, "ICBMGravityBlock", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EGuang.class, "ICBMLightBeam", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(ESuiPian.class, "ICBMFragment", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EShouLiuDan.class, "ICBMGrenade", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EFake.class, "ICBMFake", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EChe.class, "ICBMChe", EntityRegistry.findGlobalUniqueEntityId());
		
		EntityRegistry.registerModEntity(EZhaDan.class, "ICBMExplosive", ENTITY_ID_PREFIX, this, 50, 5, true);
		EntityRegistry.registerModEntity(EDaoDan.class, "ICBMMissile", ENTITY_ID_PREFIX+1, this, 100, 1, true);
		EntityRegistry.registerModEntity(EZhaPin.class, "ICBMProceduralExplosion", ENTITY_ID_PREFIX+2, this, 100, 5, true);
		EntityRegistry.registerModEntity(EFeiBlock.class, "ICBMGravityBlock", ENTITY_ID_PREFIX+3, this, 50, 15, true);
		EntityRegistry.registerModEntity(EGuang.class, "ICBMLightBeam", ENTITY_ID_PREFIX+4, this, 80, 5, true);
		EntityRegistry.registerModEntity(ESuiPian.class, "ICBMFragment", ENTITY_ID_PREFIX+5, this, 40, 8, true);
		EntityRegistry.registerModEntity(EShouLiuDan.class, "ICBMGrenade", ENTITY_ID_PREFIX+6, this, 50, 5, true);
		EntityRegistry.registerModEntity(EFake.class, "ICBMFake", ENTITY_ID_PREFIX+7, this, 50, 5, true);
		EntityRegistry.registerModEntity(EChe.class, "ICBMChe", ENTITY_ID_PREFIX+8, this, 50, 4, true);
		
  	    //Register potion effects
		PoisonRadiation.register();
		PDaDu.INSTANCE.register();
		PChuanRanDu.INSTANCE.register();
		PDongShang.INSTANCE.register();
  	    
		this.proxy.init();
    }

	public static Vector3 getLook(float rotationYaw, float rotationPitch)
    {
        float var2;
        float var3;
        float var4;
        float var5;

        var2 = MathHelper.cos(-rotationYaw * 0.017453292F - (float)Math.PI);
        var3 = MathHelper.sin(-rotationYaw * 0.017453292F - (float)Math.PI);
        var4 = -MathHelper.cos(-rotationPitch * 0.017453292F);
        var5 = MathHelper.sin(-rotationPitch * 0.017453292F);
        return new Vector3(var3 * var4, var5, var2 * var4);
    }

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event)
	{
		ICommandManager commandManager = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
		ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager); 
		serverCommandManager.registerCommand(new MingLing());
		
		BaoHu.nbtData = BaoHu.loadData("ICMB");
	}
	
	@ServerStopping
	public void serverStopping(FMLServerStoppingEvent event)
	{
		BaoHu.saveData(BaoHu.nbtData, "ICMB");
	}
}