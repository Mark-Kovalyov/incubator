CREATE OR REPLACE FUNCTION torrent_time_updater()
RETURNS trigger AS
$$
     DECLARE
     BEGIN
          RAISE NOTICE 'NEW: %', NEW;
          IF NEW.value < 0
          THEN
               NEW.value := -1;
               RETURN NEW;
          END IF;
          RETURN NEW;
     END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER
BEFORE INSERT OR UPDATE ON torrent_info