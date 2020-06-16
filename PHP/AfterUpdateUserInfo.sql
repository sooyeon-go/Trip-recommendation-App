DELIMITER $$
CREATE TRIGGER AfterUpdateUserInfo
AFTER UPDATE ON User_Info
FOR EACH ROW
BEGIN
	UPDATE Bookmark_StudioInsert set u_email = new.u_email WHERE u_email =old.u_email;
END $$
DELIMITER ;

