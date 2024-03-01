CREATE TABLE "can_teach_ensemble" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "value" VARCHAR(50)
);

ALTER TABLE "can_teach_ensemble" ADD CONSTRAINT PK_can_teach_ensemble PRIMARY KEY ("id");


CREATE TABLE "contact_person" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "first_name" VARCHAR(100) NOT NULL,
 "last_name" VARCHAR(100) NOT NULL,
 "phone_number" VARCHAR(20) NOT NULL
);

ALTER TABLE "contact_person" ADD CONSTRAINT PK_contact_person PRIMARY KEY ("id");


CREATE TABLE "person" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "first_name" VARCHAR(100) NOT NULL,
 "last_name" VARCHAR(100) NOT NULL,
 "person_number" VARCHAR(12) UNIQUE NOT NULL,
 "street" VARCHAR(100),
 "zip_code" VARCHAR(5),
 "city" VARCHAR(100)
);

ALTER TABLE "person" ADD CONSTRAINT PK_person PRIMARY KEY ("id");


CREATE TABLE "phone_number" (
 "phone_number" VARCHAR(20) NOT NULL,
 "person_id" INT NOT NULL REFERENCES "person" ON DELETE CASCADE
);

ALTER TABLE "phone_number" ADD CONSTRAINT PK_phone_number PRIMARY KEY ("phone_number","person_id");


CREATE TABLE "skill_level" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "skill" VARCHAR(20)
);

ALTER TABLE "skill_level" ADD CONSTRAINT PK_skill_level PRIMARY KEY ("id");

CREATE TABLE "instrument_type" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "type" VARCHAR(50)
);

ALTER TABLE "instrument_type" ADD CONSTRAINT PK_instrument_type PRIMARY KEY ("id");

CREATE TABLE "genre" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "name" VARCHAR(50)
);

ALTER TABLE "genre" ADD CONSTRAINT PK_genre PRIMARY KEY ("id");

CREATE TABLE "brand" (
 "id" INT GENERATED ALWAYS AS IDENTITY,
 "name" VARCHAR(50)
);

ALTER TABLE "brand" ADD CONSTRAINT PK_brand PRIMARY KEY ("id");

CREATE TABLE "student" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "student_id" VARCHAR(100) UNIQUE NOT NULL,
 "person_id" INT NOT NULL REFERENCES "person",
 "skill_level_id" INT NOT NULL REFERENCES "skill_level"
);

ALTER TABLE "student" ADD CONSTRAINT PK_student PRIMARY KEY ("id");


CREATE TABLE "student_payment" (
 "student_id" INT NOT NULL REFERENCES "student",
 "payment_id" VARCHAR(100) UNIQUE NOT NULL,
 "payment_amount" INT,
 "no_of_lessons" INT,
 "payment_date" TIMESTAMP(10)
);

ALTER TABLE "student_payment" ADD CONSTRAINT PK_student_payment PRIMARY KEY ("student_id");


CREATE TABLE "contact_person_student" (
 "student_id" INT NOT NULL REFERENCES "student" ON DELETE CASCADE,
 "contact_person_id" INT NOT NULL REFERENCES "contact_person" ON DELETE CASCADE
);

ALTER TABLE "contact_person_student" ADD CONSTRAINT PK_contact_person_student PRIMARY KEY ("student_id","contact_person_id");


CREATE TABLE "discount" (
 "student_id" INT NOT NULL REFERENCES "student" ON DELETE CASCADE,
 "percentage" INT
);

ALTER TABLE "discount" ADD CONSTRAINT PK_discount PRIMARY KEY ("student_id");


CREATE TABLE "instructor" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "employee_id" VARCHAR(100) UNIQUE NOT NULL,
 "person_id" INT NOT NULL REFERENCES "person",
 "can_teach_ensemble_id" INT NOT NULL REFERENCES "can_teach_ensemble"
);

ALTER TABLE "instructor" ADD CONSTRAINT PK_instructor PRIMARY KEY ("id");


CREATE TABLE "instructor_payment" (
 "instructor_id" INT NOT NULL REFERENCES "instructor",
 "payment_id" VARCHAR(100) UNIQUE NOT NULL,
 "payment_amount" INT,
 "no_of_lessons" INT,
 "payment_date" TIMESTAMP(10)
);

ALTER TABLE "instructor_payment" ADD CONSTRAINT PK_instructor_payment PRIMARY KEY ("instructor_id");


CREATE TABLE "instructor_skill_level" (
 "instructor_id" INT NOT NULL REFERENCES "instructor" ON DELETE CASCADE,
 "skill_level_id" INT NOT NULL REFERENCES "skill_level" ON DELETE CASCADE
);

ALTER TABLE "instructor_skill_level" ADD CONSTRAINT PK_instructor_skill_level PRIMARY KEY ("instructor_id","skill_level_id");


CREATE TABLE "instrument_to_teach" (
 "instrument_type_id" INT NOT NULL REFERENCES "instrument_type" ON DELETE CASCADE,
 "instructor_id" INT NOT NULL REFERENCES "instructor" ON DELETE CASCADE
);

ALTER TABLE "instrument_to_teach" ADD CONSTRAINT PK_instrument_to_teach PRIMARY KEY ("instrument_type_id","instructor_id");


CREATE TABLE "lesson" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "room_id" VARCHAR(100) NOT NULL,
 "instructor_id" INT NOT NULL REFERENCES "instructor",
 "session_id" VARCHAR(100) NOT NULL,
 "date" DATE NOT NULL
);

ALTER TABLE "lesson" ADD CONSTRAINT PK_lesson PRIMARY KEY ("id");

CREATE TABLE "lesson_type" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "type" VARCHAR(20)
);

ALTER TABLE "lesson_type" ADD CONSTRAINT PK_lesson_type PRIMARY KEY ("id");

CREATE TABLE "lesson_fee" (
 "lesson_id" INT NOT NULL REFERENCES "lesson" ON DELETE CASCADE,
 "lesson_cost" INT,
 "skill_level_id" INT NOT NULL REFERENCES "skill_level",
 "lesson_type_id" INT NOT NULL REFERENCES "lesson_type"
);

ALTER TABLE "lesson_fee" ADD CONSTRAINT PK_lesson_fee PRIMARY KEY ("lesson_id");

CREATE TABLE "rent_instrument" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "rental_id" VARCHAR(100) UNIQUE NOT NULL,
 "start_date" TIMESTAMP(10) NOT NULL,
 "end_date" TIMESTAMP(10) NOT NULL,
 "student_id" INT NOT NULL REFERENCES "student"
);

ALTER TABLE "rent_instrument" ADD CONSTRAINT PK_rent_instrument PRIMARY KEY ("id");


CREATE TABLE "rental_fee" (
 "rent_instrument_id" INT NOT NULL REFERENCES "rent_instrument" ON DELETE CASCADE,
 "rental_cost" INT NOT NULL
);

ALTER TABLE "rental_fee" ADD CONSTRAINT PK_rental_fee PRIMARY KEY ("rent_instrument_id");


CREATE TABLE "sibling" (
 "id" INT GENERATED ALWAYS AS IDENTITY NOT NULL,
 "sibling_id" VARCHAR(100) UNIQUE NOT NULL,
 "student_id" INT NOT NULL REFERENCES "student" ON DELETE CASCADE,
 "person_id" INT NOT NULL REFERENCES "person" ON DELETE CASCADE
);

ALTER TABLE "sibling" ADD CONSTRAINT PK_sibling PRIMARY KEY ("id");

CREATE TABLE "student_lesson" (
 "student_id" INT NOT NULL REFERENCES "student" ON DELETE CASCADE,
 "lesson_id" INT NOT NULL REFERENCES "lesson" ON DELETE CASCADE
);

ALTER TABLE "student_lesson" ADD CONSTRAINT PK_student_lesson PRIMARY KEY ("student_id","lesson_id");


CREATE TABLE "availability" (
 "instructor_id" INT NOT NULL REFERENCES "instructor" ON DELETE CASCADE,
 "time" TIMESTAMP(10) NOT NULL
);

ALTER TABLE "availability" ADD CONSTRAINT PK_availability PRIMARY KEY ("instructor_id");


CREATE TABLE "ensemble" (
 "lesson_id" INT NOT NULL REFERENCES "lesson",
 "genre_id" INT NOT NULL REFERENCES "genre",
 "max_num_of_students" INT,
 "min_num_of_students" INT
);

ALTER TABLE "ensemble" ADD CONSTRAINT PK_ensemble PRIMARY KEY ("lesson_id");


CREATE TABLE "group_lesson" (
 "lesson_id" INT NOT NULL REFERENCES "lesson",
 "instrument_type_id" INT NOT NULL REFERENCES "instrument_type" ON DELETE CASCADE,
 "no_of_places" INT,
 "min_num_of_students" INT,
 "skill_level_id" INT NOT NULL REFERENCES "skill_level"
);

ALTER TABLE "group_lesson" ADD CONSTRAINT PK_group_lesson PRIMARY KEY ("lesson_id");


CREATE TABLE "individual_lesson" (
 "lesson_id" INT NOT NULL REFERENCES "lesson",
 "instrument_type_id" INT NOT NULL REFERENCES "instrument_type" ON DELETE CASCADE,
 "time" TIMESTAMP(10) NOT NULL,
 "skill_level_id" INT NOT NULL REFERENCES "skill_level"
);

ALTER TABLE "individual_lesson" ADD CONSTRAINT PK_individual_lesson PRIMARY KEY ("lesson_id");


CREATE TABLE "instrument" (
 "rent_instrument_id" INT NOT NULL REFERENCES "rent_instrument",
 "instrument_id" VARCHAR(100) UNIQUE NOT NULL,
 "instrument_type_id" INT NOT NULL REFERENCES "instrument_type" ON DELETE CASCADE,
 "brand_id" INT REFERENCES "brand",
 "quantity_in_stock" INT
);

ALTER TABLE "instrument" ADD CONSTRAINT PK_instrument PRIMARY KEY ("rent_instrument_id");


CREATE TABLE "time_slot" (
 "lesson_id" INT NOT NULL REFERENCES "lesson" ON DELETE CASCADE,
 "start_time" TIMESTAMP(10),
 "end_time" TIMESTAMP(10)
);

ALTER TABLE "time_slot" ADD CONSTRAINT PK_time_slot PRIMARY KEY ("lesson_id");
