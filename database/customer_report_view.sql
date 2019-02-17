CREATE VIEW flight_reservation.customer_report_view AS
SELECT c.customer_id AS customer_id,
       c.first_name  AS first_name,
       c.last_name   AS last_name
FROM flight_reservation.customer c;