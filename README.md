# 🌀 LapisPortalsPlus

**LapisPortalsPlus** is a lightweight, fully physical portal system for Paper 1.21.8 servers.  
Players can build frame-based portals (similar to Nether portals) using customizable blocks like Lapis, Diamond, or Glowstone.  
Each portal is **linked automatically** through a chest key system — no commands required.

---

## ✨ Features

- 🧱 **Physical Frame Detection**
  - Build rectangular frames with supported materials.
  - Frames can use customizable blocks defined in `config.yml`.

- 🗝️ **Chest-Based Linking**
  - Place a single chest adjacent to the frame.
  - Two portals with **matching chest contents** automatically link.
  - If a key item is already used by two portals, the builder is notified.

- 💨 **Teleportation**
  - Step into the portal to teleport instantly to its linked partner.
  - Cooldown and teleport delay are configurable.
  - Optional teleport particle and lightning effects.

- 🔮 **Particles & Visuals**
  - Linked portals display a subtle **edge outline** (always on).
  - Optional **interior particle effect** for a swirling magical look.
  - All visual effects are configurable and toggleable.

- 🧠 **Admin Debug Mode**
  - `/lpp debug [on|off]`  
    Enables detailed logging of link logic and key pairing.
  - Permission: `lapisportalsplus.admin`

- 💥 **Explosion Safety**
  - Creeper or TNT explosions that destroy part of a frame or its chest will automatically invalidate the portal.

- 🗂️ **Simple YAML Persistence**
  - All portals are stored in `config.yml`.
  - Auto-rebuild works after restarts or reloads.

---

## ⚙️ Configuration

### `config.yml` Overview
```yaml
frame_blocks:
  - LAPIS_BLOCK
  - DIAMOND_BLOCK
  - GLOWSTONE

teleport_cooldown_seconds: 2

# Outline around linked portals
linked_outline:
  enabled: true
  interval_ticks: 15
  density: 0.5

# Optional interior swirl
interior_particles:
  enabled: false
  type: ENCHANTMENT_TABLE
  count: 8
  interval_ticks: 15
