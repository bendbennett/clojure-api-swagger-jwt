-- :name associate-user-application-group :! :n
-- :doc Insert a user-application-group association
insert into users_applications_groups (user_id, application_id, group_id)
values (:user_id, :application_id, :group_id);
