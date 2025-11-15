/* View para Animais e Suas Classes (animalclass) 
select * from animalclass;*/

select *
from class
inner join animal on ani_cla_id = cla_id;

/* View para Atividades e Áreas (activity_area_view) 
select * from activity_area_view;*/

select 
    ac.ac_id, 
    ac.ac_name, 
    ac.ac_schedule, 
    ac.ac_cap as capacity, 
    a.area_name as area_name
from activity ac
inner join area a on ac.ac_area_id = a.area_id;

/*View para Enclosures e Animais (enclosure_animals_view)
select * from enclosure_animals_view;*/

select 
    e.enc_name, 
    e.enc_lat, 
    e.enc_long, 
    e.enc_mapsid, 
    a.ani_name
from enclosure e
inner join ae ae on e.enc_id = ae.ae_enc_id
inner join animal a on ae.ae_ani_id = a.ani_id;

/*View para Animais Favoritos de Pessoas (favorite_animals_view)
select * from favorite_animals_view;*/

select 
    f.fav_id, 
    f.fav_animal, 
    a.ani_name, 
    p.per_name
from favorite f
inner join animal a on f.fav_ani_id = a.ani_id
inner join person p on f.fav_per_id = p.per_id;

/*View para Estoque de Quiosques (kiosk_stock_view)
select * from kiosk_stock_view;*/

select 
    k.kio_name, 
    p.pro_name, 
    s.stock_amount
from stock s
inner join product p on s.stock_pro_id = p.pro_id
inner join kiosk k on s.stock_kio_id = k.kio_id;

/*View para Produtos e Estoques (product_stock_view)
select * from product_stock_view;*/

select 
    p.pro_id, 
    p.pro_name, 
    p.pro_price, 
    s.stock_amount, 
    k.kio_name as kiosk_name
from stock s
inner join product p on s.stock_pro_id = p.pro_id
inner join kiosk k on s.stock_kio_id = k.kio_id;

/*View para Sub-Áreas e Atividades (sub_area_activities_view))
select * from sub_area_activities_view;*/

select 
    sa.sa_name, 
    ac.ac_name, 
    ac.ac_schedule
from sub_area sa
inner join area a on sa.sa_area_id = a.area_id
inner join activity ac on ac.ac_area_id = a.area_id;

/*View para Visitas e Informações de Pessoas (visit_details_view)
select * from visit_details_view*/
select 
    v.vi_id, 
    v.vi_dtime as visit_time, 
    p.per_name, 
    p.per_email, 
    sa.sa_name as sub_area_name
from visited v
inner join person p on v.vi_per_id = p.per_id
inner join sub_area sa on v.vi_sa_id = sa.sa_id;


/*Visitas por Sub-Área e Horários Populares
Objetivo: Listar as sub-áreas mais visitadas, o número de visitas e o horário mais comum dessas visitas. */

select
    sa.sa_name as sub_area_name,
    COUNT(v.vi_id) as visit_count,
    HOUR(v.vi_dtime) as popular_hour
from visited v
inner join sub_area sa on v.vi_sa_id = sa.sa_id
group by sa.sa_name, hour(v.vi_dtime)
order by visit_count desc, popular_hour asc;


/*Estoque de Produtos por Quiosque com Alertas de Baixo Estoque
Objetivo: Identificar produtos com baixo estoque (menos de 10 unidades) em cada quiosque.*/

select
    k.kio_name as kiosk_name,
    p.pro_name as product_name,
    s.stock_amount as quantity_in_stock
from stock s
inner join product p on s.stock_pro_id = p.pro_id
inner join kiosk k on s.stock_kio_id = k.kio_id
where s.stock_amount < 100
order by s.stock_amount asc, k.kio_name asc;


/*Animais Favoritos Mais Populares
Objetivo: Determinar os animais mais favoritos com base no número de vezes que foram marcados como favoritos.*/

select
    a.ani_name as animal_name,
    COUNT(f.fav_id) as favorite_count
from favorite f
inner join animal a on f.fav_ani_id = a.ani_id
where f.fav_animal = true
group by a.ani_name
order by favorite_count desc;


/*Atividades Agendadas em Áreas Específicas
Objetivo: Obter atividades programadas em uma área específica (area_name) e o número de visitantes esperados.*/

select 
    ac.ac_name as activity_name,
    ac.ac_schedule as activity_time,
    ac.ac_cap as expected_visitors,
    a.area_name as area_name
from activity ac
inner join area a on ac.ac_area_id = a.area_id
where a.area_name = 'Savanna' -- Substituir pelo nome da área desejada
order by ac.ac_schedule asc;


/*Sub-áreas mais visitadas
Objetivo: Obter as sub-áreas mais visitadas, incluindo o total de visitas.*/

select 
    sa.sa_name as sub_area_name, 
    COUNT(v.vi_id) as total_visits
from visited v
inner join sub_area sa on v.vi_sa_id = sa.sa_id
group by sa.sa_name
order by total_visits desc;


/*Pessoas com Mais Visitas ao Zoológico
Objetivo: Listar os visitantes mais frequentes e o total de visitas de cada um.*/

select
    p.per_name as person_name,
    p.per_email as email,
    COUNT(v.vi_id) as total_visits
from visited v
inner join person p on v.vi_per_id = p.per_id
group by p.per_id, p.per_name, p.per_email
order by total_visits desc;


/*Atividades e Sub-Áreas Relacionadas
Objetivo: Encontrar atividades vinculadas a sub-áreas específicas e suas áreas principais.*/

select
    sa.sa_name as sub_area_name,
    a.area_name as area_name,
    ac.ac_name as activity_name,
    ac.ac_schedule as activity_time
from sub_area sa
inner join area a on sa.sa_area_id = a.area_id
inner join activity ac on ac.ac_area_id = a.area_id
order by sa.sa_name, ac.ac_schedule asc;










