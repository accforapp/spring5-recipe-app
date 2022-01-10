create table category (id bigint not null auto_increment, description varchar(255), primary key (id)) engine = InnoDB;
create table ingredient (id bigint not null auto_increment, amount decimal(19,2), description varchar(255), recipe_id bigint, uom_id bigint, primary key (id)) engine = InnoDB;
create table notes (id bigint not null auto_increment, recipe_notes longtext, recipe_id bigint, primary key (id)) engine = InnoDB;
create table recipe (id bigint not null auto_increment, cook_time integer, description varchar(255), difficulty varchar(255), directions longtext, image longblob, prep_time integer, servings integer, source varchar(255), url varchar(255), notes_id bigint, primary key (id)) engine = InnoDB;
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine = InnoDB;
create table unit_of_measure (id bigint not null auto_increment, description varchar(255), primary key (id)) engine InnoDB;

alter table ingredient add constraint FK_ingredient_recipe foreign key (recipe_id) references recipe (id);
alter table ingredient add constraint FK_ingredient_uom foreign key (uom_id) references unit_of_measure (id);
alter table notes add constraint FK_notes_recipe foreign key (recipe_id) references recipe (id);
alter table recipe add constraint FK_recipe_notes foreign key (notes_id) references notes (id);
alter table recipe_category add constraint FK_recipe_category_recipe foreign key (recipe_id) references recipe (id);
alter table recipe_category add constraint FK_recipe_category_category foreign key (category_id) references category (id);create table category (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB


