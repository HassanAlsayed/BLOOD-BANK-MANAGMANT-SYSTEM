CREATE TRIGGER insertdonor 
BEFORE INSERT ON donor 
FOR EACH ROW 
SET NEW.id = (SELECT MAX(id) + 1 FROM donor);
					
CREATE TRIGGER id_blood 
BEFORE INSERT ON bloodbank 
FOR EACH ROW 
SET NEW.id_blood = (SELECT MAX(id_blood) + 1 FROM bloodbank),NEW.id_d = (SELECT MAX(id_d) + 1 FROM bloodbank);


CREATE TRIGGER insertPatient 
BEFORE INSERT ON patient 
FOR EACH ROW 
SET NEW.id = (SELECT MAX(id) + 1 FROM patient);
