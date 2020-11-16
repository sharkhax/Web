# Web
# How it works:
Doctor registers a new patient, creates a new record. In the record creation he can specify a diagnosis and choose one of the treatments - procedure or surgery are available. Surgery can be executed by doctor only, procedure - by assistant as well. After curing doctor decides to discharge a patient or to create a new record either.
# Project features:
  - User friendly URL
  - Localization: en_EN, ru_BY
  - 3 user's roles
  - Used Bootstrap for FE
  - 8 custom tags
  - Annotation was used in commands to mark user's access level
  - Custom connection pool and proxy connections
  - Transactions
 # Guest's scope:
  - Signing in
  - Changing a locale
 # Assistant's scope:
  - All guest's abilities
  - Viewing a personal information
  - Changing password
  - Viewing a patient list
  - Sorting the patient list and using pagination
  - Viewing patient's information
  - Executing a procedure for the patient
  - Viewing a record list of the patient
  - Sorting the record list and using pagination
  - Viewing record's information
 # Doctor's scope:
  - All assistant's abilities
  - Creating a new patient
  - Executing a surgery
  - Discharging a patient
  - Creating a new record
  - Updating the patient
 # Admin's scope:
  - All doctor's abilities
  - Creating a new user
  - Viewing a user list
  - Sorting the user list and using pagination
  - Viewing an employee list
  - Sorting the employee list and using pagination
  - Viewing user's information
  - Updating the user (excluding a password)
  - Updating user's password
  - Blocking, unblocking user
  - Viewing employee's information
  - Updating the employee
  - Firing, restoring the employee
  - Sending to and returning employee from the vacation
