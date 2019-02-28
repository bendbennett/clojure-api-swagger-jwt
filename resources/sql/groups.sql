-- :name insert-group :! :n
-- :doc Insert a single group
insert into groups (id, name)
values (:id, :name);

-- :name find-group-by-name :? :1
-- :doc Get group by name
select * from groups
where name = :name
