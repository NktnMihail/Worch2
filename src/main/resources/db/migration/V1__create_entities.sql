CREATE TABLE IF NOT EXISTS "user"(
    id UUID,
    phone VARCHAR(15),
    first_name VARCHAR(255)  NOT NULL,
    last_name VARCHAR(255),
    image BYTEA,
    birthday TIMESTAMP WITH TIME ZONE,
    language VARCHAR(20),
    created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS channel(
    id UUID,
    name VARCHAR(255)       NOT NULL,
    description VARCHAR(255),
    is_private BOOLEAN,
    password VARCHAR(255),
    age_restricted BOOLEAN,
    owner_id UUID,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at     TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_channel PRIMARY KEY (id),
    CONSTRAINT fk_channel_owner FOREIGN KEY (owner_id) REFERENCES "user" (id)
);

CREATE TABLE IF NOT EXISTS "group"(
    id UUID,
    name VARCHAR(255),
    owner_id UUID,
    parent_id UUID,
    created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_group PRIMARY KEY (id),
    CONSTRAINT fk_group_owner FOREIGN KEY (owner_id) REFERENCES "user" (id),
    CONSTRAINT fk_group_parent FOREIGN KEY (parent_id) REFERENCES "group" (id)

);

CREATE TABLE IF NOT EXISTS group_user(
    group_id UUID,
    user_id UUID,

    CONSTRAINT pk_group_user PRIMARY KEY (group_id, user_id)
);

CREATE TABLE IF NOT EXISTS choice(
    id UUID,
    creator_id UUID,
    channel_id UUID,
    title VARCHAR(255),
    description TEXT,
    image BYTEA,
    is_personal BOOLEAN,
    status VARCHAR(20),
    deadline TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_choice PRIMARY KEY (id),
    CONSTRAINT fk_choice_creator FOREIGN KEY (creator_id) REFERENCES "user" (id),
    CONSTRAINT fk_choice_channel FOREIGN KEY (channel_id) REFERENCES channel (id),
    CONSTRAINT chk_choice_status CHECK (status IN ('active', 'closed', 'archived'))
);

CREATE TABLE IF NOT EXISTS choice_option (
    id UUID,
    choice_id UUID NOT NULL,
    text VARCHAR(255),
    position INT,

    CONSTRAINT pk_choice_option PRIMARY KEY (id),
    CONSTRAINT fk_choice_option_choice FOREIGN KEY (choice_id) REFERENCES choice (id)
);

CREATE TABLE IF NOT EXISTS vote (
    id UUID,
    choice_id UUID,
    option_id UUID,
    user_id UUID,
    voted_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_vote PRIMARY KEY (id),
    CONSTRAINT fk_vote_choice FOREIGN KEY (choice_id) REFERENCES choice (id),
    CONSTRAINT fk_vote_option FOREIGN KEY (option_id) REFERENCES choice_option (id),
    CONSTRAINT fk_vote_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE IF NOT EXISTS expert_application(
    id UUID,
    user_id UUID,
    motivation VARCHAR(255),
    status VARCHAR(20),
    submitted_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_expert_application PRIMARY KEY (id),
    CONSTRAINT fk_expert_application_user FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT chk_expert_application_status CHECK (status IN ('NEW', 'APPROVED', 'REJECTED', 'IN_PROGRESS'))
);

CREATE TABLE IF NOT EXISTS expert_profile(
    id UUID,
    user_id UUID,
    is_incognito BOOLEAN,
    price INTEGER,
    rating FLOAT,

    CONSTRAINT pk_expert_profile PRIMARY KEY (id),
    CONSTRAINT fk_expert_profile_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE IF NOT EXISTS stack(
    id UUID,
    title VARCHAR(255),
    description TEXT,
    creator_id UUID,
    is_quiz BOOLEAN,
    published BOOLEAN,
    created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_stack PRIMARY KEY (id),
    CONSTRAINT fk_stack_creator FOREIGN KEY (creator_id) REFERENCES "user" (id)
);

CREATE TABLE IF NOT EXISTS stack_item(
    id UUID,
    stack_id UUID,
    choice_id UUID,
    position INT,
    
    CONSTRAINT pf_stack_item PRIMARY KEY (id),
    CONSTRAINT fk_stack_item_stack FOREIGN KEY (stack_id) REFERENCES stack (id),
    CONSTRAINT fk_stack_item_choice FOREIGN KEY (choice_id) REFERENCES choice (id)
);


