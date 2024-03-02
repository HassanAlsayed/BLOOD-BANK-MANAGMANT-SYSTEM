create user donors;
grant insert on bloodbankmanagmentsystem.donor to donors;

create user patients;
grant insert on bloodbankmanagmentsystem.patient to patient;

create user admin;
grant update,delete,select on bloodbankmanagmentsystem.patient to admin;
grant update,delete,select on bloodbankmanagmentsystem.donor to admin;
grant select on bloodbankmanagmentsystem.bloodBank to admin;