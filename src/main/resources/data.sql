--Cards
insert into public.card (id, create_date, card_type, description, path_icon, title)
values (nextval('hibernate_sequence'), now(), 'TESTER', 'Привлекательная', 'Пэрис Хилтон.jpg', 'Пэрис Хилтон');
insert into public.card (id, create_date, card_type, description, path_icon, title)
values (nextval('hibernate_sequence'), now(), 'ANALYTIC', 'Хорошо забивает', 'Дэвид Бекхэм.jpg', 'Дэвид Бекхэм');
insert into public.card (id, create_date, card_type, description, path_icon, title)
values (nextval('hibernate_sequence'), now(), 'DEVELOPER', 'Хорошо придумывает', 'Стивен Спилберг.jpeg', 'Стивен Спилберг');
insert into public.card (id, create_date, card_type, description, path_icon, title)
values (nextval('hibernate_sequence'), now(), 'TEAM_LEAD', 'Много печенек влезает в рот', 'Камерон Диаз.jpg', 'Камерон Диаз');

--Questions
insert into public.question (id, create_date, text, type, used, user_id)
values (nextval('hibernate_sequence'), now(), 'Автотестирование', 'TESTER', false, null);
insert into public.question (id, create_date, text, type, used, user_id)
values (nextval('hibernate_sequence'), now(), 'Золотое сечение', 'ANALYTIC', false, null);
insert into public.question (id, create_date, text, type, used, user_id)
values (nextval('hibernate_sequence'), now(), 'Полиморфизм', 'DEVELOPER', false, null);
insert into public.question (id, create_date, text, type, used, user_id)
values (nextval('hibernate_sequence'), now(), 'Спека', 'TESTER', false, null);
insert into public.question (id, create_date, text, type, used, user_id)
values (nextval('hibernate_sequence'), now(), 'UML диаграмма', 'ANALYTIC', false, null);
insert into public.question (id, create_date, text, type, used, user_id)
values (nextval('hibernate_sequence'), now(), 'Рефлексия', 'DEVELOPER', false, null);