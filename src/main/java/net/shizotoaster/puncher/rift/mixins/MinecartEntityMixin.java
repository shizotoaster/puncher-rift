package net.shizotoaster.puncher.rift.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartEntity.class)
public abstract class MinecartEntityMixin extends Entity {
    @Shadow public abstract void setDamageWobbleStrength(float strength);

    public MinecartEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void puncher$minecartKiller(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!this.world.isClient && !this.removed && !this.isInvulnerable(damageSource)) {
            Entity attacker = damageSource.getAttacker();

            if (attacker instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) attacker;

                if (player.isSneaking()) {
                    this.setDamageWobbleStrength(100000000000000000F);
                }
            }
        }
    }
}
