CREATE OR REPLACE FUNCTION updated_at_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (NEW IS DISTINCT FROM OLD) THEN
        NEW.updated_at = NOW();
        RETURN NEW;
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;