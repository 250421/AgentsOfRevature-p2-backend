-- Ensure table exists (JPA usually handles this if ddl-auto is set)
-- Insert test users (with BCrypt-hashed passwords)

INSERT INTO users (id, password, role, username) VALUES
  (1,   '$2a$10$Q9eV2kh5Dlttc2V4kI9LRe4Ev.5jXRphI6FE/ODG0Z.JPV1eHmt5W', 'USER',  'agent007'),
  (2,   '$2a$10$ml03ENvpKYJ6AxyqZzlePuxAV6us3MC/mvZ3zSGZcK9Z6gAYU5Dsy', 'ADMIN', 'admin'),
  (3,   '$2a$10$yT1FC4DRYZDkhw2SPRx5ROyt5BoRwh7PbCS6GzSGKQfppFErln28K', 'USER',  'testuser'),
  (4,   '$2a$10$7W3PHwSOSE63456NZGq.AeuCH8r6gz49dDepMe8UJwAbDYwUXYrdi', 'USER',  'testuser2'), -- all passwords are Testtest1@
  (197, '$2a$10$NQ8VjKtY/J507WzIhhFfqeJyJsUxVDzqwSvo3FAMwMvZb2eec3krO', 'USER',  'larry'),
  (198, '$2a$10$/v8fF6qMFGE5jQaBVeZN6.zqR7dPiEmVkEzA5g0ml.mIzDryi/h9K', 'USER',  'gerald'),
  (199, '$2a$10$3Td2/siw566ZxPB5dDzrNu7ixM5gyyjoRuxCIAcYFru9oBtRuxmzu', 'USER',  'filmore'),
  (200, '$2a$10$aTWbwO.84Nk4XSB4JHVr1O7e4O2uMHS6UQGwVlW22lcI3Ll1vrqw2', 'USER',  'superman'),
  (201, '$2a$10$z3whWUOA4B.Uqu2zwf2oi.5PzUQkpAfydtoKIR2qFYQs6nahNnaNO', 'USER',  'theb@ddest'),
  (202, '$2a$10$A4/kfzh0MqM/Y5r38s8i3.SqXxPOgTwhfMnL5Nrx61R2Q7Pr6pnrW', 'USER',  'daniel'),
  (203, '$2a$10$krCRwcxt6a/uzvjcbuae1OyulMh5dYrUfLFPE3SOn2yu7KfsLSSia', 'USER',  'carlos'),
  (204, '$2a$10$JsL4WH.cUFTF4Yhwx8ONQeW0mD32xXZT4TlOaAWoCbnKe/xmV9vcu', 'USER',  'luis1'),
  (205, '$2a$10$Bdpng4F7gQ3hi9K/Zax2Z.MH6ZA/anN6YS55tQdKv.ykWCt6eWLQy', 'USER',  'rose1'),
  (206, '$2a$10$slJT6QliTTTbthIk9TMjuu8NmJdSEEPo40rDrq/PgbjU5GyPtZxVO', 'USER',  'josh1')
ON CONFLICT (username) DO NOTHING;


-- src/main/resources/data.sql

-- 5 CRITICAL calamities
INSERT INTO calamities (title, reported, location, description, villain, severity) VALUES
  ('Shock Assault', '5 mins ago', 'Metropolis, Daily Planet Plaza',
   'A massive shockwave ripped through the plaza, sending bystanders scrambling under falling debris.', 'Brainiac', 'CRITICAL'),
  ('City Blackout', '12 mins ago', 'Gotham, Wayne Tower',
   'All power systems in the tower failed simultaneously, plunging corridors into absolute darkness.', 'Doomsday', 'CRITICAL'),
  ('Cataclysmic Breach', '20 mins ago', 'Central City, Particle Accelerator',
   'An enormous tear in reality swallowed lab equipment whole, leaving a crackling void behind.', 'Galactus', 'CRITICAL'),
  ('Quantum Rift', '8 mins ago', 'Star Labs, Cortex Wing',
   'A sudden ripple in the air distorted physics near the core reactor, warping everything around.', 'Darkseid', 'CRITICAL'),
  ('Orbital Crash', '15 mins ago', 'Guardians Base, Orbital Dock',
   'An unidentified vessel slammed into the docking ring, shattering panels across the deck.', 'Thanos', 'CRITICAL')
ON CONFLICT (title) DO NOTHING;

-- 5 HIGH calamities
INSERT INTO calamities (title, reported, location, description, villain, severity) VALUES
  ('Laughing Shadows', '30 mins ago', 'Gotham, Ace Chemicals',
   'A hall of vats filled with noxious gas that induces uncontrollable laughter has breached containment.', 'Joker', 'HIGH'),
  ('Metal Storm', '25 mins ago', 'New York, Stark Industries',
   'Every piece of metal in the facility has been drawn to a pulsating core, fusing doors shut with furnace heat.', 'Magneto', 'HIGH'),
  ('Machine Revolt', '18 mins ago', 'San Francisco, Pym Labs',
   'Automated defense turrets have turned hostile, targeting anyone approaching the main entrance.', 'Ultron', 'HIGH'),
  ('Silent Sentinel', '22 mins ago', 'Wakanda Embassy, Tech Wing',
   'Energy shields are cycling on and off, trapping personnel inside and cutting off communications.', 'Black Panther', 'HIGH'),
  ('Black Tide', '35 mins ago', 'Oscorp R&D Center',
   'A dark, viscous fluid has flooded the labs, corroding surfaces in seconds and whispering through vents.', 'Venom', 'HIGH')
ON CONFLICT (title) DO NOTHING;

-- 5 MEDIUM calamities
INSERT INTO calamities (title, reported, location, description, villain, severity) VALUES
  ('Mind Maze', '40 mins ago', 'Star City, Queen Consolidated',
   'Encrypted puzzles have overridden the security network, locking down vital systems without warning.', 'Riddler', 'MEDIUM'),
  ('Pumpkin Storm', '45 mins ago', 'Queens, Oscorp Tower',
   'Shattered glass and unspent ordnance litter the penthouse suite after an unseen barrage.', 'Green Goblin', 'MEDIUM'),
  ('Steel Snare', '50 mins ago', 'Empire City, Dockyards',
   'Heavy-duty cranes have tangled their own cables into deadly nooses across the loading docks.', 'Doctor Octopus', 'MEDIUM'),
  ('Tidal Surge', '55 mins ago', 'Atlantis Outpost, Reef Lab',
   'A sudden wall of water breached the outer barrier, flooding experiments and shorting circuits.', 'Black Manta', 'MEDIUM'),
  ('High Stakes', '60 mins ago', 'Hell’s Kitchen, Devil’s Den',
   'Illicit gaming tables have activated hidden traps, ensnaring participants under the floor panels.', 'Kingpin', 'MEDIUM')
ON CONFLICT (title) DO NOTHING;

-- 5 LOW calamities
INSERT INTO calamities (title, reported, location, description, villain, severity) VALUES
  ('Whispered Fear', '65 mins ago', 'Coast City, Ferris Dynamics',
   'Employees report sudden waves of panic near the main power conduits, despite no visible threat.', 'Sinestro', 'LOW'),
  ('Ledger Vanish', '70 mins ago', 'Gotham, Wayne Manor',
   'Ancient account books have disappeared overnight, leaving only cryptic ledger entries behind.', 'Bane', 'LOW'),
  ('Radiant Flicker', '75 mins ago', 'Harlem, Banner Labs',
   'Gamma sensors spiked and dropped in seconds, as if something hulking brushed past the detectors.', 'Abomination', 'LOW'),
  ('Rail Jolt', '80 mins ago', 'Queens, Subway Station',
   'Platform rails emitted unexpected high-voltage arcs, startling commuters but causing no lasting harm.', 'Shocker', 'LOW'),
  ('Tea Whistle', '85 mins ago', 'London, Clock Tower',
   'Steam valves in the control room hissed in a rhythmic pattern, unsettling the on-duty technicians.', 'Mad Hatter', 'LOW')
ON CONFLICT (title) DO NOTHING;

INSERT INTO results (id, did_win, rep_gained, calamity_id, scenario_id, user_id) VALUES
  (1,  false,  0,  10,  1,   4),
  (2,  false,  0,   1,  3, 206),
  (3,  false,  0,   2,  4, 206),
  (4,  false,  0,   3,  5, 206),
  (5,  false,  0,   4,  6, 205),
  (6,  false,  0,  18,  7, 205),
  (7,  false,  0,  12,  8, 202),
  (8,  true,  15,  17,  9, 202),
  (9,  true,  12,  19, 10, 203),
  (10, true,  10,  15, 11, 204)
ON CONFLICT (id) DO NOTHING;


-- 10 example scenarios
INSERT INTO scenarios
    (id, chapter_count, closing, complete, point_total, calamity_id, hero_selection_id, user_id)
VALUES
    (2,  1,
     $$$$$$, -- empty closing
     false, 0, 1, 2, 206),
    (8,  6,
     $$The heroes, focusing on apprehending Green Goblin, left the inferno raging. Goblin, surprised by the direct assault, was briefly taken off guard but quickly recovered, unleashing a barrage of pumpkin bombs and sonic attacks. Despite the heroes' valiant efforts, the mission came at great cost—several city blocks lay in ruin by the time they subdued him.$$,
     true,  8,  12, 8,  202),
    (1,  6,
     $$The gamble was a desperate one. Spider‑Man, with Venom hot on his heels, skillfully maneuvered towards the Skull Ship. But Venom, sensing the impending danger, swerved at the last moment, narrowly avoiding the brunt of the energy blast. The weapon fired, shattering half the vessel—but your team survived to tell the tale.$$,
     true, 10, 10, 1,  4),
    (11, 6,
     $$Mario, now shimmering with invincibility, plunged into the pit. The monstrous figure below was a large hydraulic press, slowly rising to crush the remaining support structure of the casino. With his brief invulnerability, Mario was able to damage the press and escape unscathed.$$,
     true, 10, 15, 11, 204),
    (9,  6,
     $$With the origin of the virus pinpointed, Batman, Iron Man, and Wolverine work in perfect synchronicity. While Batman uploads a counter‑virus, immunizing the global network and neutering Bane's digital weapon, Iron Man reinforces Gotham’s crumbling firewalls.$$,
     true, 15, 17, 9,  202),
    (3,  6,
     $$Despite Mario's valiant efforts to inspire hope, Brainiac's psychic assault proves too strong, the accumulated power from the energy collector overwhelming the heroes' defenses. The trapped civilians were freed just before the collider’s meltdown.$$,
     true,  9,  1,  3,  206),
    (4,  6,
     $$Frodo's desperate attempt to banish Doomsday proves futile. The creature shrugs off the Ring's power, its rage undeterred, and with a final, earth‑shattering blow, Doomsday tears the control panel apart. The tower’s structural integrity fails; you evacuate just in time.$$,
     true,  7,  2,  4,  206),
    (10, 6,
     $$Batman's sudden appearance on the train roof throws Shocker off balance, disrupting his final attack. The two engage in a brutal hand‑to‑hand fight amidst the chaos, the tilting train adding another layer of danger. Batman prevails, ordering an emergency stop.$$,
     true, 12, 19, 10, 203),
    (5,  6,
     $$Frodo lunged forward, the One Ring pulsing with dark energy, but Galactus swatted him aside like an insect. The Ring clattered away, its power ineffective against the cosmic being. With a triumphant roar, Galactus unleashed his final blast, obliterating the installation.$$,
     true,  8,  3,  5,  206),
    (6,  6,
     $$The gamble failed spectacularly as Sailor Moon's redirection attempt only managed to slightly alter the course of the Omega Beams, causing them to strike the already weakened magical shields at a different angle. The shields shattered instantly.$$,
     true,  8,  4,  6,  205)
ON CONFLICT (id) DO NOTHING;


-- 11 example hero_selections
INSERT INTO hero_selections
    (id, hero1, hero2, hero3, calamity_id, user_id)
VALUES
    (1,  'Batman',       'Spider-Man',  'Wonder Woman',   10,  4),
    (2,  'Mario',        'Harry-Potter','Mr.-Clean',      1,   206),
    (3,  'Frodo',        'Mario',       'Batman',         1,   206),
    (4,  'Frodo',        'Mario',       'Batman',         2,   206),
    (5,  'Frodo',        'Mario',       'Batman',         3,   206),
    (6,  'Harry-Potter','Sailor-Moon', 'Kermit-the-Frog',4,   205),
    (7,  'Harry-Potter','Sailor-Moon', 'Wonder-Woman',   18,  205),
    (8,  'Batman',       'Wolverine',   'Ironman',        12,  202),
    (9,  'Batman',       'Wolverine',   'Ironman',        17,  202),
    (10, 'Batman',       'The-Flash',   'Cyclops',        19,  203),
    (11, 'Sonic',        'Donkey-Kong', 'Mario',          15,  204)
ON CONFLICT (id) DO NOTHING;



