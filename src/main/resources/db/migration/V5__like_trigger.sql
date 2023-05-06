CREATE OR REPLACE FUNCTION comment_emotion_count_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    if TG_OP = 'INSERT'  then
        if NEW.status = 'LIKE'  then
          update comment set like_count = like_count + 1 where id = NEW.comment_id;
            return NEW;
     else
          update comment set dislike_count = dislike_count + 1 where id = NEW.comment_id;
            return NEW;
    end if;
  end if;
return like_count;
END;
$$;

CREATE TRIGGER comment_emotion_count_trigger
    BEFORE INSERT OR UPDATE
    ON comment_like
    FOR EACH ROW
EXECUTE PROCEDURE comment_emotion_count_function();