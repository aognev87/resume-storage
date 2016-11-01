DELETE FROM resume;
DELETE FROM contact;

INSERT INTO resume (uuid, full_name) VALUES
  ('9a88d05d-7586-4431-921d-2eb30a586760', 'name1'),
  ('ae23bd77-d663-41c0-9d73-69d6185679d2', 'name2'),
  ('cb6a575f-d97c-47e8-8c40-f6c26c7d650c', 'name3');

INSERT INTO contact (id, resume_uuid, type, value) VALUES
  ('1', '9a88d05d-7586-4431-921d-2eb30a586760', 'PHONE', '123456'),
  ('2', '9a88d05d-7586-4431-921d-2eb30a586760', 'SKYPE', 'skype');