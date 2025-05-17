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

-- 4. Scenarios (no dollar‑quoting; internal ' become '')
INSERT INTO scenarios
  (id, chapter_count, closing, complete, point_total, calamity_id, hero_selection_id, user_id)
VALUES
  -- empty closing
  (2, 1, '',          false, 0,  1,  2, 206),
  -- long closings with '' escaping
  (8, 6,
    'The heroes, focusing on apprehending Green Goblin, left the inferno raging. Goblin, surprised by the direct assault, was briefly taken off guard but quickly recovered, unleashing a barrage of pumpkin bombs and sonic attacks. Despite the heroes'' valiant efforts, the mission came at great cost—several city blocks lay in ruin by the time they subdued him.',
    true,  8, 12,  8, 202),
  (1, 6,
    'The gamble was a desperate one. Spider‑Man, with Venom hot on his heels, skillfully maneuvered towards the Skull Ship. But Venom, sensing the impending danger, swerved at the last moment, narrowly avoiding the brunt of the energy blast. The weapon fired, sending a shockwave that almost tore half the vessel apart—but your team survived to tell the tale.',
    true, 10, 10,  1,   4),
  (11, 6,
    'Mario, now shimmering with invincibility, plunged into the pit. The monstrous figure below was a large hydraulic press, slowly rising to crush the remaining support structure of the casino. With his brief invulnerability, Mario was able to damage the press and escape unscathed.',
    true, 10, 15, 11, 204),
  (9, 6,
    'With the origin of the virus pinpointed, Batman, Ironman, and Wolverine work in perfect synchronicity. While Batman uploads a counter‑virus, immunizing the global markets and neutering Bane''s digital weapon, Ironman reinforces Gotham’s crumbling defenses.',
    true, 15, 17,  9, 202),
  (3, 6,
    'Despite Mario''s valiant efforts to inspire hope, Brainiac''s psychic assault proves too strong, the accumulated power from the energy collector overwhelming the heroes'' defenses. The trapped civilians were freed just before the collider’s meltdown.',
    true,  9,  1,  3, 206),
  (4, 6,
    'Frodo''s desperate attempt to banish Doomsday proves futile. The creature shrugs off the Ring''s power, its rage undeterred, and with a final, earth‑shattering blow, Doomsday tears the control panel apart. The tower''s structural integrity fails, and you must evacuate just in time.',
    true,  7,  2,  4, 206),
  (10, 6,
    'Batman''s sudden appearance on the train roof throws Shocker off balance, disrupting his final attack. The two engage in a brutal hand‑to‑hand fight amidst the chaos, the tilting train adding another layer of danger. Batman prevails, ordering an emergency stop just in time.',
    true, 12, 19, 10, 203),
  (5, 6,
    'Frodo lunged forward, the One Ring pulsing with dark energy, but Galactus swatted him aside like an insect. The Ring clattered away, its power ineffective against the cosmic being. With a triumphant roar, Galactus unleashed his final blast, obliterating the installation.',
    true,  8,  3,  5, 206),
  (6, 6,
    'The gamble failed spectacularly as Sailor Moon''s redirection attempt only managed to slightly alter the course of the Omega Beams, causing them to strike the already weakened magical shields at a different angle. The shields shattered instantly.',
    true,  8,  4,  6, 205),
  (7, 6,
    'Sailor Moon unleashed her ultimate attack, a dazzling blast of lunar energy, but it was too late. Abomination, empowered by the mutated alligators and fueled by rage, swatted the blast aside like an annoying insect. Harry Potter''s protective spells flickered harmlessly.',
    true,  7, 18,  7, 205)
ON CONFLICT (id) DO NOTHING;

-- 51 example story_points
INSERT INTO story_points
    (id, chapter_number, text, scenario_id)
VALUES
    (1,  1, 'The Black Tide seeps from the breached lab doors, consuming everything in its path with unnerving speed. Alarms blare as scientists scream, struggling against the adhesive ooze. The air crackles with corrupted energy as Venom''s presence looms, a dark whisper promising assimilation.', 1),
    (2,  1, 'The plaza lies in ruins, a testament to Brainiac''s devastating arrival. The towering Coluan has landed his Skull Ship in the heart of the city, a vessel crackling with stolen energy. He announces his intention to bottle the metropolis, adding it to his collection of shrunken civilizations. Resistance is met with blasts of disruptive energy, scattering heroes and civilians alike.', 2),
    (3,  2, 'The breach attempt was partially successful. Spider-Man managed to web up some of the Skull Ship''s internal defense systems, slowing its progress, but the Black Tide has now spread further, consuming entire blocks. Buildings are dissolving, and the acidic fumes are making breathing difficult. Batman reports the Skull Ship''s main weapon is charging, threatening a city-wide disintegration blast.', 1),
    (4,  3, 'The delayed evacuation bought precious time, but the Black Tide is now flowing into the sewer systems, threatening to destabilize the entire city''s infrastructure. The Skull Ship''s weapon charges, its energy signature spiking dangerously. Venom appears on a nearby skyscraper, a grotesque grin on his face as he watches the chaos unfold. "Such delectable despair!" he booms, the Black Tide responding to his will, surging faster. Batman confirms the disintegration blast is imminent, with only minutes remaining before it fires.', 1),
    (5,  4, 'The heroes'' combined assault bought crucial seconds. The Skull Ship''s weapon flickered, its energy discharge momentarily disrupted. However, the Black Tide, emboldened by Venom, lashed out, encasing Wonder Woman in a constricting tendril. She struggles against its corrosive touch, while Spider-Man and Batman are forced to divide their attention between freeing her and preventing the weapon from fully recharging. Venom cackles, the city’s fate hanging precariously in the balance.', 1),
    (6,  5, 'Venom, enraged by Spider-Man’s taunts, lunges off the skyscraper, a tidal wave of Black Tide surging behind him. Batman struggles to free Wonder Woman, the Skull Ship''s weapon visibly stabilizing, its energy build-up reaching a critical threshold. The city shudders under the immense strain. The fate of Metropolis rests on this final gambit.', 1),
    (7,  1, 'The plaza is in ruins, energy crackling in the air as Brainiac''s ship looms overhead. He announces, "This city will be dissected, analyzed, and added to my collection." Robotic probes swarm from the ship, targeting key infrastructure.', 3),
    (8,  2, 'Brainiac''s ship unleashes a concentrated energy beam, vaporizing a city block as Batman frantically analyzes the probe''s tech. He discovers a potential frequency overload vulnerability but needs time to implement a countermeasure. Meanwhile, swarms of Brainiac''s drones continue to wreak havoc, disabling emergency services and trapping civilians.', 3),
    (9,  3, 'Brainiac’s ship reels from the energy surge, momentarily disrupting the drone swarm. However, Brainiac adapts quickly, redirecting power to reinforce the ship''s shields. A new, larger wave of drones descends, these ones equipped with energy dampeners, effectively negating the frequency vulnerability. The drones begin constructing a massive energy collector in the center of the plaza, drawing power from the city itself.', 3),
    (10, 4, 'The energy collector flickers as Frodo, against all odds, manages to destabilize it with the ring''s power. Brainiac''s ship unleashes a barrage of energy blasts targeting Frodo while the energy dampening drones begin converging on his location. The collector, though damaged, is still drawing power, and the city''s lights are dimming. The drones will overwhelm Frodo shortly unless he receives immediate support. Brainiac transmits a city-wide broadcast, "Your futile resistance is amusing. Observe the true potential of collected knowledge!" A holographic projection shows various captured cities, each preserved in miniature, suspended within energy fields.', 3),
    (11, 5, 'Brainiac, enraged by the previous disruptions, now focuses his full attention on eliminating the heroes. He deploys a powerful energy field around the plaza, trapping the remaining heroes and civilians. The energy collector pulses with stolen power, amplifying Brainiac''s mental abilities. He begins to psychically assault the minds of the trapped civilians, causing widespread panic and chaos, while targeting the heroes for direct mental subjugation.', 3),
    (12, 1, 'The pitch-black corridors of the tower reek of ozone. Emergency lights flicker sporadically, casting distorted shadows. Through the darkness, a monstrous figure crashes through a reinforced door – Doomsday, radiating heat and rage. He roars, a sound that seems to vibrate the very foundations of the building. He seems disoriented but highly aggressive, clearly the cause of the blackout.', 4),
    (13, 2, 'The gamble with Mario paid off, momentarily distracting Doomsday. The creature, however, proved too powerful. Mario is down, but alive and being tended to by Frodo. Doomsday, now fully acclimated to the darkness, advances toward the tower''s central control room, smashing obstacles in his path. The emergency generators are about to fail, which will activate a failsafe to flood the entire tower.', 4),
    (14, 3, 'Frodo''s attempt to use the Ring has a limited effect. Doomsday momentarily pauses, confused, but quickly recovers, his rage amplified. The emergency generators sputter violently, their lights dimming further. Water begins to seep into the lower levels, a chilling prelude to the imminent flood. Doomsday, now seemingly drawn to the failing generators, continues his rampage.', 4),
    (15, 4, 'The attempt to destroy the generators succeeds, averting the immediate threat of the flood. However, the chaotic energy released by their destruction seems to invigorate Doomsday. He throws a chunk of debris at Batman as he is escaping with a crowd of people. The Dark Knight is hit and now incapacitated! Doomsday now stands before the main control panel of the tower, poised to deliver a devastating blow.', 4),
    (16, 5, 'Doomsday reels back, momentarily disoriented by the Ring''s influence, but the effect is fleeting. He roars again, smashing the control panel with a force that sends sparks flying. The tower begins to shake violently, sections of the ceiling collapsing. Doomsday, seemingly immune to the structural damage he’s causing, prepares to rip the entire control panel from its moorings, potentially causing the entire tower to collapse.', 4),
    (17, 1, 'The Cataclysmic Breach looms, a swirling vortex of impossible colors consuming everything it touches. Gravity fluctuates wildly, pulling debris and structures towards the void. From the heart of the chaos, a colossal figure emerges, silhouetted against the swirling energies – Galactus, Devourer of Worlds! His booming voice echoes, "This world will sustain me!" He reaches for the planet with a hand wreathed in cosmic power.', 5),
    (18, 2, 'The beam fired by Batman struck the breach, causing a momentary ripple. Galactus recoiled, roaring in anger. The breach pulsed, becoming more unstable. The beam seems to have wounded Galactus but also worsened the immediate problem. Chunks of our world are being pulled through now as the breach widens. More lab equipment is sucked in, then a car, then a section of the building.', 5),
    (19, 3, 'The ground buckles as a school bus vanishes into the ever-growing breach. Galactus, weakened but undeterred, extends his hand further, cosmic energy crackling around his fingers. He begins to siphon energy from Earth''s core, causing violent tremors.', 5),
    (20, 4, 'The magical surge from Frodo rips through the breach, causing a feedback loop. The tear in reality thrashes violently, spitting out twisted remnants of other dimensions. Galactus stumbles backward, clutching his head in pain. The connection to Earth’s core weakens, but the breach continues to expand erratically, now spewing forth bizarre creatures and landscapes.', 5),
    (21, 5, 'The heroes'' all-out assault left Galactus staggered, but the dimensional instability is spiraling out of control. The breach is now a gaping maw, spewing forth alien flora, monstrous creatures, and fragments of shattered realities. Galactus, enraged and weakened, prepares one final, devastating energy blast to consume what remains of Earth, focusing his energy to bypass the breach altogether. The fate of the world hangs in the balance.', 5),
    (22, 1, 'A quantum rift has torn open above the Metropolis Nuclear Power Plant. Gravity fluctuates wildly, objects phase in and out of existence, and radiation levels are spiking. From the chaotic tear in reality emerges Darkseid, his eyes burning with malevolent intent. "This world will become an extension of Apokolips!" he booms, unleashing a wave of energy that further destabilizes the rift. The plant is on the verge of a catastrophic meltdown.', 6),
    (23, 2, 'The magical shields Harry Potter erected are holding, but the radiation levels are still dangerously high, threatening to overwhelm the barriers. Darkseid, meanwhile, has begun tearing chunks of the power plant free and hurling them through the quantum rift, presumably to Apokolips. Gravity continues to fluctuate wildly, making movement treacherous. Sailor Moon narrowly avoids being crushed by a falling turbine. The situation is rapidly deteriorating; the rift is expanding, threatening to engulf the entire city.', 6),
    (24, 3, 'The chunks of debris Sailor Moon managed to vaporize helped stabilize the immediate vicinity, but Darkseid seems amused by the resistance. He raises his hand, and the quantum rift pulses, spawning miniature black holes that begin to orbit the power plant. These miniature singularities warp space-time even further, pulling everything towards them with immense force and making Harry''s shielding efforts even more challenging. The radiation is spiking again as the reactor core groans under the strain.', 6),
    (25, 4, 'Harry Potter''s reality bubble is strained but holding, buying precious time. However, the miniature black holes Darkseid conjured are proving incredibly disruptive. Kermit is struggling to even move against the distorted gravity. Darkseid, hovering above the chaos, unleashes his Omega Beams, targeting Harry''s protective field. The beams strike, causing visible cracks in the magical barrier. The reactor core is nearing critical mass. If the beams break through, the magical field will collapse, and the subsequent radiation surge would be catastrophic.', 6),
    (26, 5, 'Darkseid laughs as Sailor Moon''s energy bolsters Harry''s shields, but the Omega Beams continue to pound against the magical barrier. The reactor core is groaning, and the miniature black holes are tugging relentlessly at everything. Kermit, despite the chaos, croaks, "We need to stop him, not just defend!" The rift above pulses, growing larger, and Darkseid prepares another blast of Omega Beams.', 6),
    (27, 1, 'A Radiant Flicker washes over downtown Metropolis, leaving a faint green residue and a lingering smell of ozone. Before the authorities can react, a news report shows grainy footage of a hulking, green figure smashing through LexCorp Tower. Abomination has arrived! It''s time to deploy your team and contain the threat.', 7),
    (28, 2, 'Wonder Woman engages Abomination, holding him back from causing further destruction, while civilians evacuate. The Green residue on the other hand has seeped into the sewers and seems to be empowering the local wildlife. The alligators are getting bigger, meaner, and glowing green.', 7),
    (29, 3, 'The magically contained alligators thrash against Harry Potter''s spells, the green glow intensifying. Abomination, sensing a shift in the city''s energy, breaks free of Wonder Woman''s grasp and leaps towards the sewers, roaring, "More! MORE POWER!" He crashes into the sewer entrance, reinforcing the glowing creatures and beginning to absorb their energy. Wonder Woman struggles to keep up, hampered by the mutated alligators clawing her legs.', 7),
    (30, 4, 'Abomination, now noticeably larger and radiating gamma energy, stands triumphant amidst the glowing alligators. His eyes lock onto Harry Potter, sensing the magic binding the creatures. Wonder Woman, weakened but determined, struggles to rise. The creatures under Abomination''s influence begin to break free from their magical prison.', 7),
    (31, 5, 'Abomination, now a towering behemoth of gamma energy, lunges towards Harry Potter and Wonder Woman, his fist a green blur. Sailor Moon chants, gathering lunar energy for a final, desperate attack. The remaining alligators, freed from their weakening magical prison, snap and hiss, eager to rejoin their empowered master. It''s the final showdown.', 7),
    (32, 1, 'The shattered remains of Jack Lancaster''s penthouse shimmer under emergency lights. Lancaster, CEO of a major weapons manufacturer, is slumped over his desk, miraculously alive but bleeding. The air crackles with residual energy. Through the wrecked panoramic window, a cackling figure on a goblin-shaped glider soars away against the stormy Gotham skyline. It''s Green Goblin!', 8),
    (33, 2, 'The energy signature analysis pinpoints Green Goblin''s likely destination: an abandoned Oscorp facility on the outskirts of Gotham. As the heroes approach, the building erupts in green flames. Goblin''s cackling echoes through the night, announcing the arrival of pumpkin bombs raining down on the surrounding area. He appears to be trying to collapse the building in a fiery demolition.', 8),
    (34, 3, 'The Oscorp facility is now a raging inferno. From the heart of the blaze, Green Goblin emerges atop his glider, dodging debris with manic glee. He unleashes another volley of pumpkin bombs, this time targeting nearby emergency vehicles. Firefighters struggle to contain the blaze as Goblin’s taunts echo through the night. “Come and get me, heroes! This is just a warm-up!”', 8),
    (35, 4, 'The containment field holds, but the intense heat begins to tax Iron Man''s systems. Green Goblin, perched atop a nearby skyscraper, unleashes a sonic scream from his glider, shattering windows and momentarily disrupting the containment field. Firefighters stumble, and the blaze threatens to breach the barrier. Goblin drops a single, oversized pumpkin bomb directly into the heart of the inferno.', 8),
    (36, 5, 'The oversized pumpkin bomb detonates, sending a shockwave that nearly shatters Iron Man’s containment field. The fire roars, threatening to consume the surrounding blocks. Green Goblin, still perched atop the skyscraper, begins lobbing smaller, incendiary pumpkin bombs into the streets below, sowing chaos and panic. The firefighters, though safe, are demoralized by the setback. He''s attempting to stretch your resources to the breaking point and the inferno is now completely uncontained.', 8),
    (37, 1, 'Gotham City is in chaos. Not from explosions or supervillains, but from… accounting? Every major financial institution has reported their ancient ledgers vanished overnight, replaced by pages of maddening symbols. The only witness? A terrified night watchman muttering about "shadows and a masked giant." A single calling card was left: A broken back symbol. Bane has weaponized… economics? This is going to be a long week.', 9),
    (38, 2, 'Batman''s analysis reveals Bane isn''t just stealing ledgers, he''s manipulating historical financial data. The cryptic symbols are ancient economic indicators. By altering these records, Bane aims to induce a city-wide economic depression, giving him control through chaos. News reports start flooding in; small businesses are failing, the stock market is plummeting, and panic is spreading like wildfire. Wolverine catches wind of a planned riot downtown, fueled by Bane''s machinations.', 9),
    (39, 3, 'The economic panic is escalating. As you continue to decipher Bane''s economic strategy, Ironman reports multiple structural failures across Gotham, seemingly tied to destabilized building loans and insurance policies. Wolverine arrives from the riot, battered but having contained the immediate threat. He warns that public trust is eroding; people are desperate and blaming the heroes for failing to stop the collapse. Bane''s voice booms across the city''s emergency channels, offering "order" in exchange for loyalty.', 9),
    (40, 4, 'Bane''s voice still echoes across the city, promising stability in exchange for submission. The failures across Gotham continue, buildings cracking and teetering dangerously. Ironman is stretched thin, trying to reinforce critical structures and prevent further collapses. Wolverine, despite his healing factor, is visibly exhausted from quelling riots and rescuing citizens. The pressure is mounting, and the ledger analysis remains incomplete, a frustrating puzzle with missing pieces.', 9),
    (41, 5, 'The final pieces of Bane''s ledger system lock into place. It reveals his ultimate goal: not just to cripple Gotham, but to use the city''s financial ruin as a catalyst to destabilize global markets. The pressure is immense: the city is in shambles, the world watches, and Bane''s propaganda fills the air. The final ledger page shows that Bane intends to trigger an economic meltdown, using a digital virus programmed to exploit a single, ancient banking regulation still active worldwide.', 9),
    (42, 1, 'A wave of electric panic washes over the city as Shocker unveils his latest scheme: embedding high-voltage generators beneath subway platforms, causing harmless but disruptive electric arcs. Commuters are more frightened than injured, but the chaos is escalating quickly. News helicopters circle as Shocker cackles from a nearby rooftop, his gauntlets crackling with power.', 10),
    (43, 2, 'The Flash''s speed proves crucial in disabling most of the generators, but Shocker, anticipating this, remotely supercharges one final device directly beneath a packed commuter train. The train screeches to a halt, its passengers trapped and terrified as crackling electricity courses through the third rail. Shocker, still on the rooftop, broadcasts a gloating message: if his demands aren''t met, he''ll overload the generator and fry the train.', 10),
    (44, 3, 'The train hangs precariously close to the sparking generator. Cyclops'' optic blast effectively cut the main power lines, but Shocker has rerouted power through a secondary system, causing the train''s internal electrical systems to malfunction. Lights flicker, doors jam, and panic rises among the passengers. Shocker, clearly agitated by the setback, threatens to trigger a full system failure, releasing a lethal surge into the train cars.', 10),
    (45, 4, 'The Flash''s efforts prove partially successful, diverting a significant portion of the surge. However, a smaller, more focused electrical current still threatens to overload the train''s control systems. Shocker, now visibly frustrated, unleashes a powerful shockwave from his gauntlets towards the train. The shockwave is not lethal, but it''s designed to disable the train entirely, leaving the passengers stranded and at his mercy. He shouts that this is their last chance to meet his demands.', 10),
    (46, 5, 'The Flash barely manages to mitigate the shockwave, but the train''s emergency brakes lock up, throwing passengers from their seats. Sparks fly from the control panel, and the train begins to tilt dangerously on the tracks. Shocker, enraged at the continued defiance, leaps from the rooftop onto the train''s roof, intending to personally deliver a final, devastating blast. The train is teetering, the passengers are terrified, and Shocker is closing in.', 10),
    (47, 1, 'A surge of panicked reports floods your comms: high-stakes gamblers trapped! Initial scans reveal pressure-activated traps beneath the floor of the Kingpin''s lavish casino, ensnaring patrons. Kingpin''s taunting voice booms over the casino''s PA system, "The house always wins, Agent. Care to bet your heroes can save them all?" It''s a trap, no doubt, but lives are on the line.', 11),
    (48, 2, 'Donkey Kong''s brute force is proving effective, tearing through the pressure-sensitive floor panels. However, with each panel ripped, the remaining floor becomes more unstable. Kingpin''s voice cackles, "Such... enthusiasm! But for every panel broken, the pressure increases elsewhere! Soon, the whole place goes BOOM!" The remaining gamblers are even more terrified, screaming about new cracks appearing around them.', 11),
    (49, 3, 'The reinforcements from Sonic are holding, but the casino''s structure groans under the strain. Kingpin’s laughter echoes, “Impressive! But my house has more than one trick! I''ve activated a secondary system – a series of automated turrets programmed to target anyone moving too fast! Enjoy the lead shower!" Gunfire erupts from hidden compartments in the walls, targeting Sonic specifically.', 11),
    (50, 4, 'Mario’s daring diversion worked, momentarily silencing the turrets. Sonic, with his incredible speed, is now circling the casino, attempting to hack into the turret control system. But Kingpin anticipated this. “Foolish Agent! Those turrets are on a closed loop! You cannot hack them! But perhaps you’ll enjoy THIS!" The casino floor begins to tilt violently, sending panicked gamblers sliding towards a massive, open pit in the center.', 11),
    (51, 5, 'The gears controlling the tilt mechanism hiss and melt under Mario''s fire, halting the casino floor''s rotation. Gamblers cling desperately to anything they can find, but the pit looms large and ominous. Kingpin''s voice, laced with venom, booms, "You may have stalled me, Agent, but my plan has many layers! The pit is not the end, it''s the BEGINNING! Prepare for your final gamble!" A monstrous figure begins to rise from the darkness below.', 11)
ON CONFLICT (id) DO NOTHING;

-- Insert story_point_options if not already present
INSERT INTO story_point_options (id, points, text, story_point_id) VALUES
  (1,  2, 'Analyze the Black Tide''s composition to find a weakness.',                                                                                       1),
  (2,  1, 'Focus on rescuing trapped scientists, delaying the tide''s spread.',                                                                                1),
  (3,  3, 'Engage Venom directly to stop the source of the Black Tide.',                                                                                        1),
  (4,  2, 'Attempt to breach the Skull Ship''s defenses with Mario''s agility and Harry''s magic.',                                                           2),
  (5,  1, 'Focus on rescuing civilians trapped beneath the rubble using Mr. Clean''s efficiency and strength.',                                                  2),
  (6,  0, 'Launch a direct assault on Brainiac, hoping to disrupt his plans before he can shrink the city.',                                                   2),
  (7,  2, 'Focus efforts on containing the Black Tide to prevent further spread.',                                                                            3),
  (8,  1, 'Prioritize evacuating remaining civilians before the Skull Ship fires.',                                                                            3),
  (9,  3, 'Launch a direct assault on the Skull Ship to disrupt the weapon''s charging sequence.',                                                              3),
  (10, 2, 'Focus Spider-Man on finding Venom''s weakness and exploiting it.',                                                                                   4),
  (11, 3, 'Have Wonder Woman attempt to contain the Black Tide while Batman disables the Skull Ship''s weapon.',                                                4),
  (12, 1, 'Direct all three heroes to try and overload the Skull Ship''s weapon with a combined assault.',                                                      4),
  (13, 1, 'Focus all efforts on freeing Wonder Woman from the Black Tide''s grasp.',                                                                           5),
  (14, 2, 'Have Batman analyze the Black Tide''s composition to find a weakness.',                                                                             5),
  (15, 3, 'Direct Spider-Man to distract Venom, buying time to deal with the Skull Ship''s weapon.',                                                           5),
  (16, 2, 'Focus all efforts on Wonder Woman''s rescue, hoping to unleash her full power against the weapon.',                                                 6),
  (17, 1, 'Have Batman create a counter-frequency disruptor to sever Venom''s control over the Black Tide.',                                                    6),
  (18, 3, 'Instruct Spider-Man to lead Venom into the path of the Skull Ship''s weapon, hoping to use him as a shield.',                                         6),
  (19, 2, 'Deploy Batman to analyze Brainiac''s technology and find a weakness.',                                                                              7),
  (20, 1, 'Have Frodo lead the remaining citizens to safety, minimizing casualties.',                                                                           7),
  (21, 0, 'Send Mario to directly attack Brainiac''s ship, hoping to disrupt his plans.',                                                                        7),
  (22, 2, 'Prioritize protecting the remaining infrastructure from further energy blasts using Frodo''s resilience and Mario''s agility.',                       8),
  (23, 3, 'Initiate a direct assault on Brainiac''s ship using all available power, exploiting the discovered frequency vulnerability.',                         8),
  (24, 1, 'Focus on rescuing trapped civilians, hoping Batman can complete the countermeasure in time.',                                                         8),
  (25, 2, 'Focus Frodo on disrupting the energy collector with a targeted strike.',                                                                              9),
  (26, 1, 'Direct Mario to rescue trapped civilians while Batman develops a new countermeasure.',                                                               9),
  (27, 3, 'Have Batman attempt a direct hack of the energy collector’s core programming.',                                                                      9),
  (28, 2, 'Divert Mario to protect Frodo from the drone swarm.',                                                                                                 10),
  (29, 1, 'Use Batman to try and overload the energy collector despite the dampeners.',                                                                         10),
  (30, 0, 'Focus on evacuating remaining civilians, conceding the collector''s immediate power draw.',                                                           10),
  (31, 2, 'Attempt to break Brainiac''s psychic hold on the civilians by using Mario''s cheerful nature as a beacon of hope, countering the despair.',           11),
  (32, 3, 'Use Batman''s focused will to attempt to disrupt the energy field, freeing the trapped civilians and heroes.',                                         11),
  (33, 1, 'Have Frodo use the Ring''s power to resist Brainiac''s mental assault, creating a window for a coordinated attack.',                                    11),
  (34, 1, 'Send Frodo to scout ahead, using his small size and agility to navigate the darkness and locate a possible escape route.',                              12),
  (35, 2, 'Have Batman analyze the emergency power systems, attempting to restore at least partial lighting and identify Doomsday''s target.',                    12),
  (36, 0, 'Mario, using his jumping ability and durability, confront Doomsday directly to buy time for the others.',                                                12),
  (37, 3, 'Send Batman to reroute power from a lower, less critical sector to the emergency generators.',                                                         13),
  (38, 1, 'Frodo attempts to use the Ring''s power to cloud Doomsday''s mind and slow him down.',                                                                 13),
  (39, 2, 'Focus on evacuating any remaining personnel from the tower before the generators fail completely.',                                                      13),
  (40, 1, 'Batman uses his grappling hook to create a barricade, slowing Doomsday down while he and Frodo reroute power to the generators.',                        14),
  (41, 2, 'Frodo attempts to destroy the generators, preventing the failsafe flood, while Batman focuses on evacuating the remaining personnel.',                14),
  (42, 3, 'Batman designs a portable light cannon, using the generators'' last surges of power, and Frodo distracts Doomsday long enough to fire it.',            14),
  (43, 1, 'Use Mario''s fire abilities to disrupt Doomsday''s attack on the main control panel.',                                                               15),
  (44, 2, 'Use Frodo to try and manipulate Doomsday with the Ring to buy more time for Batman to recover.',                                                       15),
  (45, 3, 'Focus on rescuing the incapacitated Batman, leaving the control panel undefended.',                                                                    15),
  (46, 1, 'Command Mario (recovered) to use all his might to knock Doomsday away from the control panel.',                                                        16),
  (47, 2, 'Have Frodo attempt to use the Ring one last time to transport Doomsday to Mount Doom.',                                                                16),
  (48, 3, 'Batman must formulate a plan to weaponize the tower''s remaining energy against Doomsday.',                                                           16),
  (49, 2, 'Attempt to close the breach using a concentrated energy blast from Batman''s tech, hoping to sever Galactus'' connection.',                              17),
  (50, 1, 'Focus on rescuing scientists and securing the lab, minimizing immediate losses and gathering data.',                                                   17),
  (51, 3, 'Mario fearlessly jumps into the breach, attempting to seal it from the inside with his unique abilities.',                                              17),
  (52, 1, 'Focus on rescuing trapped scientists, ignoring the breach for now.',                                                                                   18),
  (53, 0, 'Frodo attempts to use the One Ring to influence Galactus, hoping to appeal to some hidden sense of mercy.',                                              18),
  (54, 3, 'Mario uses a Super Star to grow to a massive size and physically attempt to seal the breach.',                                                          18),
  (55, 1, 'Attempt to collapse the breach by overloading it with Frodo''s magic, hoping to sever its connection to Galactus'' power source.',                      19),
  (56, 2, 'Use Mario''s unique jumping abilities to navigate the chaotic gravity and plant explosives on Galactus'' armor, aiming to disrupt his energy absorption.',19),
  (57, 3, 'Focus on reinforcing the area around the breach with Batman''s tech, trying to contain its spread and buy time for a more permanent solution.',         19),
  (58, 2, 'Focus efforts on containing the dimensional fallout, sealing off affected areas.',                                                                     20),
  (59, 1, 'Attempt to exploit Galactus'' weakened state, launching a direct assault.',                                                                           20),
  (60, 3, 'Reinforce the magical barrier with Mario''s power-ups, stabilizing the breach''s edges.',                                                               20),
  (61, 2, 'Have Batman use a Batarang with a sample of Galactus''s energy to find a weakness to exploit.',                                                        21),
  (62, 0, 'Have Mario use the Super Star to distract Galactus and allow the team to escape before the planet is consumed.',                                         21),
  (63, 3, 'Have Frodo attempt to use the One Ring to corrupt Galactus and turn him against himself.',                                                               21),
  (64, 2, 'Send Sailor Moon to reinforce the dimensional barrier and stabilize the rift.',                                                                          22),
  (65, 1, 'Deploy Harry Potter to contain the radiation leak using magical shields.',                                                                              22),
  (66, 0, 'Have Kermit the Frog rally nearby citizens to assist in emergency evacuations.',                                                                        22),
  (67, 2, 'Reinforce Harry''s shields and attempt to magically seal the rift before it expands further.',                                                         23),
  (68, 1, 'Employ Sailor Moon to intercept the debris Darkseid is launching, preventing further damage.',                                                           23),
  (69, 0, 'Have Kermit attempt to reason with Darkseid, appealing to any shred of decency he might possess.',                                                      23),
  (70, 0, 'Have Kermit attempt to talk Darkseid down, appealing to any shred of decency within him.',                                                              24),
  (71, 2, 'Focus Harry''s magic on creating a stable pocket of reality around the reactor core to counteract the black holes.',                                       24),
  (72, 3, 'Sailor Moon should attack the black holes directly with her tiara and hope to somehow destroy them.',                                                   24),
  (73, 2, 'Attempt to redirect Darkseid''s Omega Beams using Sailor Moon''s tiara as a focal point.',                                                              25),
  (74, 0, 'Have Kermit-the-Frog attempt to reason with Darkseid, appealing to his sense of galactic order.',                                                       25),
  (75, 3, 'Reinforce Harry Potter''s shields with Sailor Moon''s energy, focusing on key points of weakness.',                                                    25),
  (76, 2, 'Have Kermit launch a surprise attack directly at Darkseid to disrupt his focus.',                                                                       26),
  (77, 3, 'Focus all available power into one final, desperate push to contain the reactor core and stabilize the rift, accepting heavy losses elsewhere.',           26),
  (78, 1, 'Attempt to use Sailor Moon''s power to redirect the Omega Beams back at Darkseid, gambling on a risky counterattack.',                                      26),
  (79, 2, 'Send Wonder Woman to engage Abomination directly, buying time for Sailor Moon to erect a protective barrier.',                                             27),
  (80, 1, 'Task Harry Potter with identifying Abomination''s weaknesses while Sailor Moon focuses on crowd control.',                                                27),
  (81, 0, 'Order a full evacuation and let Wonder Woman handle Abomination alone.',                                                                              27),
  (82, 2, 'Have Sailor Moon purify the sewer system.',                                                                                                           28),
  (83, 3, 'Have Harry Potter contain the mutated alligators with magic.',                                                                                          28),
  (84, 1, 'Have Wonder Woman reinforce the evacuation perimeter to prevent alligator attacks.',                                                                    28),
  (85, 3, 'Focus Sailor Moon''s energy to disrupt Abomination''s absorption, weakening the alligators and Abomination.',                                           29),
  (86, 2, 'Have Wonder Woman redirect Abomination toward a more contained location, like the desert outside the city.',                                            29),
  (87, 0, 'Attempt to reinforce Harry Potter''s spells, buying time for a full evacuation of the affected area.',                                                  29),
  (88, 2, 'Focus Sailor Moon''s energy to empower Wonder Woman and renew the attack on Abomination.',                                                             30),
  (89, 3, 'Have Harry Potter create a diversion, teleporting the alligators to a remote location to pull Abomination away from the city.',                         30),
  (90, 1, 'Focus Harry Potter''s magic defensively on himself and Wonder Woman to protect them from Abomination''s attacks while Sailor Moon prepares an ultimate attack.',30),
  (91, 3, 'Unleash Sailor Moon''s attack immediately, hoping for a decisive strike before Abomination can react.',                                                 31),
  (92, 2, 'Have Wonder Woman create a diversion, drawing Abomination''s attention away from Sailor Moon.',                                                       31),
  (93, 1, 'Attempt to use Harry Potter''s magic to teleport Abomination to a remote location.',                                                                    31),
  (94, 3, 'Analyze the residual energy signature and track Goblin''s glider using Iron Man''s tech.',                                                             32),
  (95, 2, 'Focus on securing the crime scene and gathering evidence with Batman''s detective skills.',                                                             32),
  (96, 1, 'Have Wolverine track Green Goblin''s scent, ignoring collateral damage.',                                                                              32),
  (97, 1, 'Launch a full-scale offensive to prevent Goblin from causing further destruction.',                                                                    33),
  (98, 3, 'Focus on rescuing any potential hostages inside the burning building.',                                                                                 33),
  (99, 2, 'Attempt to hack Goblin''s glider mid-flight to throw him off balance.',                                                                                 33),
  (100,2, 'Use Iron Man to create a containment field to prevent the fire from spreading and protect the firefighters.',                                            34),
  (101,1, 'Batman uses his detective skills to analyze Goblin''s attack pattern and predict his next target.',                                                    34),
  (102,0, 'Wolverine attempts to engage Goblin directly, ignoring the fire and surrounding chaos.',                                                               34),
  (103,1, 'Prioritize rescuing the firefighters, letting the fire spread slightly.',                                                                              35),
  (104,2, 'Focus on bolstering the containment field, risking the firefighters'' safety.',                                                                        35),
  (105,3, 'Batman creates a diversion using a grappling hook and smoke pellets, drawing Goblin''s attention.',                                                    35),
  (106,1, 'Attempt to apprehend Green Goblin directly before he can cause more damage to the surrounding area.',                                                   36),
  (107,3, 'Focus on re-establishing the containment field and extinguishing the blaze, leaving Goblin for later.',                                                 36),
  (108,2, 'Use Wolverine''s senses to locate any potential hostages or hidden traps within the burning buildings.',                                                  36),
  (109,3, 'Analyze the cryptic symbols in the ledgers using Batman''s detective skills to understand Bane''s plan.',                                             37),
  (110,2, 'Deploy Iron Man to safeguard the remaining financial institutions and prevent further data theft.',                                                    37),
  (111,1, 'Send Wolverine to track down the night watchman and extract more information about the "masked giant".',                                                37),
  (112,2, 'Focus on stabilizing the stock market to prevent further economic collapse using Iron Man''s resources.',                                            38),
  (113,1, 'Send Wolverine to quell the riot downtown, preventing further social unrest.',                                                                         38),
  (114,3, 'Continue deciphering Bane''s complete plan, hoping to understand the long game.',                                                                     38),
  (115,2, 'Focus Ironman''s efforts on stabilizing the most critical structural failures to prevent further disaster.',                                            39),
  (116,1, 'Send Wolverine to publicly confront Bane, hoping to force a direct confrontation and rally public support.',                                            39),
  (117,3, 'Prioritize deciphering Bane''s ultimate goal, trusting that understanding his complete plan will offer a more permanent solution.',                      39),
  (118,2, 'Attempt to immediately undermine Bane''s broadcast using Ironman''s technology to broadcast a message of hope and counter Bane''s propaganda.',          40),
  (119,1, 'Dispatch Wolverine to secure key financial institutions and prevent further data manipulation, even without a complete understanding of Bane''s plan.', 40),
  (120,3, 'Devote all resources to finishing the ledger analysis, hoping to uncover a single point of failure in Bane''s economic attack, even if it means Gotham suffers further short-term damage.', 40),
  (121,2, 'Immediately upload a counter-virus designed by Ironman, risking it being detected and adapted by Bane.',                                                41),
  (122,3, 'Use Batman to trace the origin of the digital virus and prevent it from being deployed at all.',                                                      41),
  (123,1, 'Send Wolverine to capture Bane, assuming that the virus deployment relies on Bane''s command.',                                                      41),
  (124,3, 'Have The Flash discreetly disable the generators while Batman apprehends Shocker.',                                                                   42),
  (125,1, 'Send Cyclops to clear the subway tunnels of panicked civilians while Batman confronts Shocker.',                                                      42),
  (126,2, 'Immediately engage Shocker head-on, hoping to quickly subdue him before he causes further disruption.',                                               42),
  (127,3, 'Focus Cyclops'' optic blast to sever the generator''s power lines.',                                                                                 43),
  (128,2, 'Have Batman devise a counter-frequency to disrupt Shocker''s remote control.',                                                                     43),
  (129,1, 'The Flash attempts to evacuate the train despite the electrical hazard.',                                                                            43),
  (130,1, 'Have Batman hack into the train''s control system to override Shocker''s commands.',                                                               44),
  (131,3, 'The Flash attempts to manually reroute the power surge away from the passenger cars.',                                                               44),
  (132,2, 'Cyclops focuses a weaker, wider beam to cool the overheating generator core, buying time.',                                                         44),
  (133,2, 'Have Batman deploy a Bat-drone to pinpoint and disable Shocker’s gauntlets.',                                                                       45),
  (134,3, 'Cyclops focuses his optic blast on the shockwave to dissipate its energy before it reaches the train.',                                               45),
  (135,1, 'The Flash tries to use his speed to create a barrier, absorbing the shockwave''s impact.',                                                          45),
  (136,2, 'Have Batman grapple onto the train roof and confront Shocker directly.',                                                                            46),
  (137,1, 'Cyclops focuses his optic blast to create a distraction, buying time for evacuation.',                                                             46),
  (138,3, 'The Flash quickly evacuates the passengers before the train derails.',                                                                               46),
  (139,2, 'Send Sonic to scout the casino layout at super speed, mapping the trap locations and potential exits.',                                                47),
  (140,1, 'Have Donkey Kong immediately start ripping up the floor panels to free the trapped gamblers, focusing on the loudest cries for help.',                 47),
  (141,0, 'Mario should try to negotiate with Kingpin to disable the traps in exchange for something.',                                                         47),
  (142,2, 'Have Mario assess the structural integrity of the building to predict the next point of collapse.',                                                  48),
  (143,3, 'Instruct Sonic to precisely target and reinforce the weakened sections with super-speed bracing.',                                                   48),
  (144,0, 'Order Donkey Kong to cease his rescue attempts and focus on containing the damage by piling debris strategically.',                                      48),
  (145,2, 'Use Mario''s acrobatics and power-ups to distract the turrets and create a safe path for Sonic to disable them.',                                      49),
  (146,1, 'Have Donkey Kong strategically collapse sections of the floor to bury the turrets under debris, risking further structural damage.',                  49),
  (147,0, 'Order a full evacuation and let the turrets fire, prioritizing minimal casualties, even if it means some are injured.',                             49),
  (148,2, 'Have Donkey Kong try to anchor the floor with his immense strength, buying time for the others.',                                                    50),
  (149,1, 'Instruct Mario to use his fire flower to melt the gears controlling the tilt.',                                                                    50),
  (150,3, 'Have Sonic attempt to vibrate the entire casino at a frequency that disrupts the tilting mechanism.',                                                50),
  (151,1, 'Have Donkey Kong jump into the pit to engage the new threat, giving Sonic and Mario time to secure the gamblers.',                                     51),
  (152,2, 'Focus Sonic''s speed on rescuing the remaining gamblers before engaging the threat.',                                                               51),
  (153,3, 'Mario uses a Super Star to become invincible and dive into the pit to assess and neutralize the threat.',                                              51)
ON CONFLICT (id) DO NOTHING;


-- Insert story_point_selections if not already present
INSERT INTO story_point_selections (id, chapter_number, scenario_id, selected_option_id, story_point_id) VALUES
  (1,  1,  1,   4,  2),
  (2,  2,  1,   8,  3),
  (3,  3,  1,  12,  4),
  (4,  4,  1,  15,  5),
  (5,  5,  1,  18,  6),
  (6,  1,  3,  19,  7),
  (7,  2,  3,  23,  8),
  (8,  3,  3,  25,  9),
  (9,  4,  3,  30, 10),
  (10, 5,  3,  31, 11),
  (11, 1,  4,  36, 12),
  (12, 2,  4,  38, 13),
  (13, 3,  4,  41, 14),
  (14, 4,  4,  44, 15),
  (15, 5,  4,  47, 16),
  (16, 1,  5,  49, 17),
  (17, 2,  5,  52, 18),
  (18, 3,  5,  55, 19),
  (19, 4,  5,  59, 20),
  (20, 5,  5,  63, 21),
  (21, 1,  6,  65, 22),
  (22, 2,  6,  68, 23),
  (23, 3,  6,  71, 24),
  (24, 4,  6,  75, 25),
  (25, 5,  6,  78, 26),
  (26, 1,  7,  81, 27),
  (27, 2,  7,  83, 28),
  (28, 3,  7,  87, 29),
  (29, 4,  7,  90, 30),
  (30, 5,  7,  91, 31),
  (31, 1,  8,  94, 32),
  (32, 2,  8,  97, 33),
  (33, 3,  8, 100, 34),
  (34, 4,  8, 103, 35),
  (35, 5,  8, 106, 36),
  (36, 1,  9, 109, 37),
  (37, 2,  9, 114, 38),
  (38, 3,  9, 117, 39),
  (39, 4,  9, 120, 40),
  (40, 5,  9, 122, 41),
  (41, 1, 10, 124, 42),
  (42, 2, 10, 127, 43),
  (43, 3, 10, 131, 44),
  (44, 4, 10, 135, 45),
  (45, 5, 10, 136, 46),
  (46, 1, 11, 140, 47),
  (47, 2, 11, 143, 48),
  (48, 3, 11, 145, 49),
  (49, 4, 11, 149, 50),
  (50, 5, 11, 153, 51)
ON CONFLICT (id) DO NOTHING;

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

-- after all your manual INSERTs…

SELECT setval(
  pg_get_serial_sequence('scenarios','id'),
  (SELECT COALESCE(MAX(id),0) FROM scenarios) + 1,
  false
);

SELECT setval(
  pg_get_serial_sequence('hero_selections','id'),
  (SELECT COALESCE(MAX(id),0) FROM hero_selections) + 1,
  false
);

SELECT setval(
  pg_get_serial_sequence('story_points','id'),
  (SELECT COALESCE(MAX(id),0) FROM story_points) + 1,
  false
);

SELECT setval(
  pg_get_serial_sequence('story_point_options','id'),
  (SELECT COALESCE(MAX(id),0) FROM story_point_options) + 1,
  false
);

SELECT setval(
  pg_get_serial_sequence('story_point_selections','id'),
  (SELECT COALESCE(MAX(id),0) FROM story_point_selections) + 1,
  false
);

SELECT setval(
  pg_get_serial_sequence('results','id'),
  (SELECT COALESCE(MAX(id),0) FROM results) + 1,
  false
);
