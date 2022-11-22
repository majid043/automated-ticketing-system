
INSERT INTO operator_codes (operator_code, operator_code_desc, operator_value) VALUES (1, 'Equals To', '=');
INSERT INTO operator_codes (operator_code, operator_code_desc, operator_value) VALUES (2, 'Not Equals To', '!=');
INSERT INTO operator_codes (operator_code, operator_code_desc, operator_value) VALUES (3, 'Greater Than', '>');
INSERT INTO operator_codes (operator_code, operator_code_desc, operator_value) VALUES (4, 'Greater Than or Equals To', '>=');
INSERT INTO operator_codes (operator_code, operator_code_desc, operator_value) VALUES (5, 'Less Than', '<');
INSERT INTO operator_codes (operator_code, operator_code_desc, operator_value) VALUES (6, 'Less Than or Equals To', '<=');

INSERT INTO data_types (data_type, data_type_desc) VALUES ('N', 'Numeric');
INSERT INTO data_types (data_type, data_type_desc) VALUES ('S', 'String');
INSERT INTO data_types (data_type, data_type_desc) VALUES ('D', 'Decimal');
INSERT INTO data_types (data_type, data_type_desc) VALUES ('T', 'Time');

INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (1, 'Delivery Id', 'deliveryId', 'N');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (2, 'Customer Type', 'customerType', 'S');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (3, 'Delivery Status', 'deliveryStatus', 'S');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (4, 'Expected Time', 'expectedTime', 'T');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (5, 'Distance From Destination', 'distanceFrmDest', 'D');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (6, 'Rider Rating', 'riderRating', 'N');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (7, 'Mean Time', 'meanTime', 'N');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (8, 'Destination Reach Time', 'destReachTime', 'N');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (9, 'Current Time', 'currentTime', 'T');
INSERT INTO eval_attributes (attr_id, attr_name, attr_key, data_type) VALUES (10, 'Estimated Delivery Time', 'estimatedDeliveryTime', 'T');

INSERT INTO eval_rules (rule_id, attr_id, operator_code, attr_value_type, attr_value) VALUES (1, 2, 1, 'F', 'VIP');
INSERT INTO eval_rules (rule_id, attr_id, operator_code, attr_value_type, attr_value) VALUES (2, 4, 3, 'A', '9');
INSERT INTO eval_rules (rule_id, attr_id, operator_code, attr_value_type, attr_value) VALUES (3, 10, 3, 'A', '4');
INSERT INTO eval_rules (rule_id, attr_id, operator_code, attr_value_type, attr_value) VALUES (4, 5, 4, 'F', '5');
INSERT INTO eval_rules (rule_id, attr_id, operator_code, attr_value_type, attr_value) VALUES (5, 5, 6, 'F', '5');

INSERT INTO use_cases (use_case_name, rule_id, exec_order, priority) VALUES ('High Priority! Customer is VIP', 1, 1, 1);
INSERT INTO use_cases (use_case_name, rule_id, exec_order, priority) VALUES ('High Priority! Expected time of delivery is passed', 2, 2, 1);
INSERT INTO use_cases (use_case_name, rule_id, exec_order, priority) VALUES ('Medium Priority! Estimation is greater than the expected time', 3, 3, 2);
INSERT INTO use_cases (use_case_name, rule_id, exec_order, priority) VALUES ('Medium Priority! Distance From Destination is low', 4, 4, 2);
INSERT INTO use_cases (use_case_name, rule_id, exec_order, priority) VALUES ('Low Priority! Distance From Destination is high', 5, 5, 3);

INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('VIP', 'RECEIVED', CURRENT_TIMESTAMP + 30 minute, 8, 4, 15, 12);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('Loyal', 'RECEIVED', CURRENT_TIMESTAMP - 30 minute, 7, 5, 15, 10);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('New', 'RECEIVED', CURRENT_TIMESTAMP + 30 minute, 6, 3, 20, 8);

INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('VIP', 'PREPARING', CURRENT_TIMESTAMP + 25 minute, 8, 4, 15, 12);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('Loyal', 'PREPARING', CURRENT_TIMESTAMP + 25 minute, 7, 5, 15, 10);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('New', 'PREPARING', CURRENT_TIMESTAMP + 25 minute, 6, 3, 20, 8);

INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('VIP', 'PICKEDUP', CURRENT_TIMESTAMP + 10 minute, 8, 4, 15, 12);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('Loyal', 'PICKEDUP', CURRENT_TIMESTAMP + 10 minute, 4, 5, 15, 10);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('New', 'PICKEDUP', CURRENT_TIMESTAMP + 10 minute, 6, 3, 20, 8);

INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('VIP', 'DELIVERED', CURRENT_TIMESTAMP, 8, 4, 15, 12);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('Loyal', 'DELIVERED', CURRENT_TIMESTAMP, 7, 5, 15, 10);
INSERT INTO delivery_infos (customer_type, delivery_status, expected_time, distance_frm_dest, rider_rating, mean_time, dest_reach_time) VALUES ('New', 'DELIVERED', CURRENT_TIMESTAMP, 6, 3, 20, 8);
