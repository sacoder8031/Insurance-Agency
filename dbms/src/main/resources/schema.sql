create table user(
    id int NOT NULL AUTO_INCREMENT,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone_no varchar(13) NOT NULL,
    password varchar(1000) NOT NULL,
    status varchar(14) NOT NULL,
    role varchar(100) NOT NULL,
    address longtext NOT NULL,
    PRIMARY KEY (id)
);

create table verification_token(
  id int NOT NULL AUTO_INCREMENT,
  token varchar(1000) NOT NULL,
  user_id int NOT NULL,
  created_date DATE NOT NULL,
  expiry_date DATE NOT NULL,
  PRIMARY KEY (id),
  foreign key (user_id) references user(id)
);

create table policy(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    category varchar(10) NOT NULL,
    premium int NOT NULL,
    max_claim_amount int NOT NULL,
    things_covered longtext NOT NULL,
    expiration_status varchar(15) NOT NULL,
    primary key (id)
);

create table faq(
    id int NOT NULL AUTO_INCREMENT,
    question text NOT NULL ,
    answer LONGTEXT NOT NULL ,
    on_topic varchar(15) NOT NULL,
    primary key (id)
);

create table policy_record(
    id int NOT NULL AUTO_INCREMENT,
    policy int NOT NULL,
    user_id int NOT NULL,
    expiry_date date NOT NULL,
    status varchar(15) NOT NULL,
    PRIMARY KEY (id),
    foreign key (policy) references policy(id),
    foreign key (user_id) references user(id)
);

create table vehicle(
    id int NOT NULL AUTO_INCREMENT,
    vehicle_number varchar(12) NOT NULL,
    document varchar(2000) NOT NULL,
    record_id int,
    user int NOT NULL,
    PRIMARY KEY (id),
    foreign key (user) references user(id),
    foreign key (record_id) references policy_record(id)
);

create table property(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    record_id int,
    user int NOT NULL,
    document varchar(2000) NOT NULL,
    primary key (id),
    foreign key (user) references user(id),
    foreign key (record_id) references policy_record(id)
);

create table transaction(
    id int NOT NULL AUTO_INCREMENT,
    type varchar(10) NOT NULL,
    amount int NOT NULL,
    receipt_no char(12) NOT NULL,
    record_id int NOT NULL,
    user_id int NOT NULL,
    primary key (id),
    foreign key (record_id) references policy_record(id),
    foreign key (user_id) references user(id)
);

create table vehicle_claims(
  id int NOT NULL AUTO_INCREMENT,
  damage int NOT NULL,
  amount int NOT NULL,
  status varchar(10) NOT NULL,
  date_of_loss DATE NOT NULL,
  vehicle_id int NOT NULL ,
  record_id int NOT NULL,
  primary key (id),
  foreign key (vehicle_id) references vehicle(id),
  foreign key (record_id) references policy_record(id)
);

create table vehicle_claim_docs(
    id int NOT NULL AUTO_INCREMENT,
    document varchar(2000) NOT NULL,
    vehicle_claim_id int NOT NULL,
    PRIMARY KEY (id),
    foreign key (vehicle_claim_id) references vehicle_claims(id)
);

create table health_claims(
   id int NOT NULL AUTO_INCREMENT,
   damage int NOT NULL,
   amount int NOT NULL,
   status varchar(10) NOT NULL,
   date_of_loss DATE NOT NULL,
   record_id int NOT NULL,
   primary key (id),
   foreign key (record_id) references policy_record(id)
);

create table health_claim_docs(
   id int NOT NULL AUTO_INCREMENT,
   document varchar(2000) NOT NULL,
   health_claim_id int NOT NULL,
   PRIMARY KEY (id),
   foreign key (health_claim_id) references health_claims(id)
);

create table property_claims(
   id int NOT NULL AUTO_INCREMENT,
   damage int NOT NULL,
   amount int NOT NULL,
   status varchar(10) NOT NULL,
   date_of_loss DATE NOT NULL,
   property_id int NOT NULL ,
   record_id int NOT NULL,
   primary key (id),
   foreign key (property_id) references property(id),
   foreign key (record_id) references policy_record(id)
);

create table property_claim_docs(
    id int NOT NULL AUTO_INCREMENT,
    document varchar(2000) NOT NULL,
    property_claim_id int NOT NULL,
    PRIMARY KEY (id),
    foreign key (property_claim_id) references property_claims(id)
);

create table life_insurance_claims(
    id int NOT NULL AUTO_INCREMENT,
    death_certificate varchar(2000) NOT NULL,
    record_id int NOT NULL,
    amount int NOT NULL,
    status varchar(20) NOT NULL,
    primary key (id),
    foreign key (record_id) references policy_record(id)
);

create table past_claims(
    record_id int NOT NULL,
    first_name varchar(255) NOT NULL ,
    last_name varchar(255) NOT NULL ,
    email varchar(255)NOT NULL ,
    amount int NOT NULL,
    category char(1) NOT NULL,
    date_of_claim date NOT NULL,
    asset_details varchar(1000) NOT NULL,
    PRIMARY KEY (record_id)
);