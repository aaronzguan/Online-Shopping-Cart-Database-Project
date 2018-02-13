-- STORED PROCEDURE
-- Compute the average amount of product of a brand and then use it to modify the quantity of product specified by user the in save_to_shopping_cart relation
CREATE FUNCTION func1 (vpid integer, vbrand character varying) RETURNS integer
	LANGUAGE plpgsql
AS $$
DECLARE
v_amount INTEGER;
C1 CURSOR FOR SELECT AVG(amount) FROM product WHERE brand = vbrand;
BEGIN
OPEN C1;
FETCH C1 INTO v_amount;
UPDATE save_to_shopping_cart SET quantity = v_amount WHERE pid = vpid;
RETURN v_amount;
CLOSE C1;
END
$$

-- EXECUTE
SELECT fun1(8,'Microsoft');

-- RECOVER
UPDATE save_to_shopping_cart SET quantity = 1 where pid = 8;

-- TRIGGER PROCEDURE
CREATE TABLE shoppingcart_audits(
  id SERIAL PRIMARY KEY,
  userid INT NOT NULL,
  pid INT NOT NULL,
  quantity INT NOT NULL,
  changed_on TIMESTAMP(6) NOT NULL
);

CREATE OR REPLACE FUNCTION shoppingcart_quantity_changes()
  RETURNS TRIGGER AS $$
BEGIN
  IF NEW.quantity <> OLD.quantity THEN
    INSERT INTO shoppingcart_audits (userid, pid, quantity,changed_on)
    VALUES ( OLD.userid, OLD.pid, OLD.quantity, now());
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER quantity_changes
  BEFORE UPDATE
  ON save_to_shopping_cart
  FOR EACH ROW
  EXECUTE PROCEDURE shoppingcart_quantity_changes();