CREATE TABLE IF NOT EXISTS users_applications_groups (
    user_id uuid,
    application_id uuid,
    group_id uuid,
    PRIMARY KEY (user_id, application_id, group_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (application_id) REFERENCES applications (id),
    FOREIGN KEY (group_id) REFERENCES groups (id)
);
