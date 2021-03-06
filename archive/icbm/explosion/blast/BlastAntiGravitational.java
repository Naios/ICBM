package icbm.explosion.blast;

import icbm.Reference;
import icbm.content.entity.EntityFlyingBlock;
import icbm.explosion.Blast;
import icbm.explosion.thread.ThreadSmallExplosion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import resonant.lib.transform.vector.Vector3;

public class BlastAntiGravitational extends Blast
{
    protected ThreadSmallExplosion thread;
    protected Set<EntityFlyingBlock> flyingBlocks = new HashSet<EntityFlyingBlock>();

    public BlastAntiGravitational(World world, Entity entity, double x, double y, double z, float size)
    {
        super(world, entity, x, y, z, size);
    }

    @Override
    public void doPreExplode()
    {
        if (!this.world().isRemote)
        {
            this.thread = new ThreadSmallExplosion(this.position, (int) this.getRadius(), this.exploder);
            this.thread.start();
        }

        this.world().playSoundEffect(position.x(), position.y(), position.z(), Reference.PREFIX + "antigravity", 6.0F, (1.0F + (world().rand.nextFloat() - world().rand.nextFloat()) * 0.2F) * 0.7F);
    }

    @Override
    public void doExplode()
    {
        int r = this.callCount;

        if (!this.world().isRemote && this.thread.isComplete)
        {
            int blocksToTake = 20;

            for (Vector3 targetPosition : this.thread.results)
            {
                double distance = targetPosition.add(position).magnitude();

                if (distance > r || distance < r - 2 || blocksToTake <= 0)
                    continue;

               Block block = world().getBlock(targetPosition.xi(), targetPosition.yi(), targetPosition.zi());

                if (block == null || block.getBlockHardness(world(), targetPosition.xi(), targetPosition.yi(), targetPosition.zi()) < 0)
                    continue;

                //if (block instanceof IForceFieldBlock)
                //    continue;

                int metadata = world().getBlockMetadata(targetPosition.xi(), targetPosition.yi(), targetPosition.zi());

                if (distance < r - 1 || world().rand.nextInt(3) > 0)
                {
                    this.world().setBlockToAir(targetPosition.xi(), targetPosition.yi(), targetPosition.zi());

                    targetPosition.add(0.5D);

                    if (world().rand.nextFloat() < 0.3 * (this.getRadius() - r))
                    {
                        EntityFlyingBlock entity = new EntityFlyingBlock(world(), targetPosition, block, metadata, 0);
                        world().spawnEntityInWorld(entity);
                        flyingBlocks.add(entity);
                        entity.yawChange = 50 * world().rand.nextFloat();
                        entity.pitchChange = 100 * world().rand.nextFloat();
                        entity.motionY += Math.max(0.15 * world().rand.nextFloat(), 0.1);
                    }

                    blocksToTake--;
                }
            }
        }

        int radius = (int) this.getRadius();
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(position.xi() - radius, position.yi() - radius, position.zi() - radius, position.xi() + radius, 100, position.zi() + radius);
        List<Entity> allEntities = world().getEntitiesWithinAABB(Entity.class, bounds);

        for (Entity entity : allEntities)
        {
            if (!(entity instanceof EntityFlyingBlock) && entity.posY < 100 + position.yi())
            {
                if (entity.motionY < 0.4)
                {
                    entity.motionY += 0.15;
                }
            }
        }

        if (this.callCount > 20 * 120)
        {
            this.controller.endExplosion();
        }
    }

    /** The interval in ticks before the next procedural call of this explosive
     * 
     * @return - Return -1 if this explosive does not need proceudral calls */
    @Override
    public int proceduralInterval()
    {
        return 1;
    }

    @Override
    public float getRadius()
    {
        return 15;
    }

    @Override
    public long getEnergy()
    {
        return 10000;
    }
}
