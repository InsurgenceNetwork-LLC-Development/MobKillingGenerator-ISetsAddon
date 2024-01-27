# MobKillingGenerator-ISetsAddon

This addon allows the player to earn armor fragments by killing mobs.

`Dynamic_Amount`: When this option is enabled, the system will use the entity type and amount specified in the `config.yml` file to determine how many fragments to provide.

To enable this generator, replace the fragment generator section of your set's file with the below.

```yaml
Fragment_Generator:
  Enabled: true
  Type: IGen
  Source: Slaying
  Amount_To_Give: 1
  Dynamic_Amount: true
  Chance: 100
  Physical: true
  Disabled_Worlds: []
  Give_Message: "&7You have received &fx{amount} &b&lIce Fragment"
```

Config:
```yaml
Custom_Amounts:
  - "Zombie:1"
  - "Skeleton:2"
  - "Spider:5"
  - "Blaze:10"
  - "Enderman:15"
```
