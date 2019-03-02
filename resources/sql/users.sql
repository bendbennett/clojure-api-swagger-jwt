-- :name insert-user :! :n
-- :doc Insert a single user
insert into users (id, username, password)
values (:id, :username, :password)
on conflict (username) do update
set password = :password;

-- :name delete-all-users :! :n
-- :doc Delete all users
delete from users;

-- :name find-user-by-username :? :1
-- :doc Get user by username
select * from users
where username = :username
