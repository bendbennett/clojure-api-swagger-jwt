-- :name insert-application :! :n
-- :doc Insert a single application
insert into applications (id, name)
values (:id, :name)
on conflict do nothing;

-- :name find-application-by-name :? :1
-- :doc Get application by name
select * from applications
where name = :name
