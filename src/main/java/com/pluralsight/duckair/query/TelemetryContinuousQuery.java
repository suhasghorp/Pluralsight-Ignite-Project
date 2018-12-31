package com.pluralsight.duckair.query;

import com.pluralsight.duckair.models.Telemetry;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.lang.IgniteBiPredicate;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;
import java.math.BigDecimal;

public class TelemetryContinuousQuery {
    public void runQuery() {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("DuckAirlines-server.xml")) {
            System.out.println();
            System.out.println(">>> Cache continuous query example started.");
            try (IgniteCache<String, Telemetry> cache = ignite.getOrCreateCache("TelemetryCache")) {

  /*************************************************************************************************************
   *        Beginning of simulated data that already exists within the cache                                   *
   *************************************************************************************************************/

                Telemetry telemetry = new Telemetry();
                telemetry.setRouteId("kcle_kbos");
                telemetry.setFlightNumber("DA6252");
                telemetry.setAltitude(12703);
                telemetry.setAirspeed(181);
                telemetry.setHeading(353);
                telemetry.setCurrentSegment(5);
                telemetry.setPositionLatitude(BigDecimal.valueOf(38.56100000));
                telemetry.setPositionLongitude(BigDecimal.valueOf(-86.22300000));
                cache.put("b925a432-cc34-11e8-849f-000c2995cc51", telemetry);

                telemetry = new Telemetry();
                telemetry.setRouteId("kcle_kbos");
                telemetry.setFlightNumber("DA3681");
                telemetry.setAltitude(18901);
                telemetry.setAirspeed(203);
                telemetry.setHeading(219);
                telemetry.setCurrentSegment(3);
                telemetry.setPositionLatitude(BigDecimal.valueOf(38.38750000));
                telemetry.setPositionLongitude(BigDecimal.valueOf(-82.76550000));
                cache.put("b925a918-cc34-11e8-849f-000c2995cc51", telemetry);

                telemetry = new Telemetry();
                telemetry.setRouteId("kcle_kbos");
                telemetry.setFlightNumber("DA2220");
                telemetry.setAltitude(11522);
                telemetry.setAirspeed(213);
                telemetry.setHeading(158);
                telemetry.setCurrentSegment(2);
                telemetry.setPositionLatitude(BigDecimal.valueOf(43.59620000));
                telemetry.setPositionLongitude(BigDecimal.valueOf(-85.93590000));
                cache.put("b925a953-cc34-11e8-849f-000c2995cc51", telemetry);

                telemetry = new Telemetry();
                telemetry.setRouteId("kcle_kbos");
                telemetry.setFlightNumber("DA6724");
                telemetry.setAltitude(20446);
                telemetry.setAirspeed(216);
                telemetry.setHeading(23);
                telemetry.setCurrentSegment(4);
                telemetry.setPositionLatitude(BigDecimal.valueOf(35.51660000));
                telemetry.setPositionLongitude(BigDecimal.valueOf(-83.62830000));
                cache.put("b925a982-cc34-11e8-849f-000c2995cc51", telemetry);

                telemetry = new Telemetry();
                telemetry.setRouteId("kcle_kbos");
                telemetry.setFlightNumber("DA7602");
                telemetry.setAltitude(11324);
                telemetry.setAirspeed(226);
                telemetry.setHeading(27);
                telemetry.setCurrentSegment(5);
                telemetry.setPositionLatitude(BigDecimal.valueOf(39.28750000));
                telemetry.setPositionLongitude(BigDecimal.valueOf(-86.89570000));
                cache.put("b925aa03-cc34-11e8-849f-000c2995cc51", telemetry);

                telemetry = new Telemetry();
                telemetry.setRouteId("kcle_kbos");
                telemetry.setFlightNumber("DA8435");
                telemetry.setAltitude(12911);
                telemetry.setAirspeed(204);
                telemetry.setHeading(235);
                telemetry.setCurrentSegment(4);
                telemetry.setPositionLatitude(BigDecimal.valueOf(36.59270000));
                telemetry.setPositionLongitude(BigDecimal.valueOf(-81.89410000));
                cache.put("b925aa29-cc34-11e8-849f-000c2995cc51", telemetry);

/*************************************************************************************************************
 *              End of simulated data that already exists within the cache                                   *
 *************************************************************************************************************/


                ContinuousQuery<String, Telemetry> continuousQuery = new ContinuousQuery<>();

                continuousQuery.setInitialQuery(new ScanQuery<>((s, telem) -> telem.getAltitude() > 18000));

                continuousQuery.setRemoteFilterFactory((
                        Factory<CacheEntryEventFilter<String, Telemetry>>) () -> event -> event.getValue().getAltitude() > 18000);

                continuousQuery.setLocalListener(cacheEntryEvents -> {
                    for (CacheEntryEvent<? extends String, ? extends Telemetry> e : cacheEntryEvents) {
                        System.out.println("Updated entry [key=" + e.getKey() + ", val=" + e.getValue());
                    }
                });

                try(QueryCursor<Cache.Entry<String, Telemetry>> cursor = cache.query(continuousQuery)) {
                    for(Cache.Entry<String, Telemetry> e : cursor) {
                        System.out.println("Queried existing entry [key=" + e.getKey() + ", val=" + e.getValue());
                    }

/*************************************************************************************************************
 *        Beginning of simulated data that is being added to the cache                                       *
 *************************************************************************************************************/
                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0259");
                    telemetry.setAltitude(13256);
                    telemetry.setAirspeed(215);
                    telemetry.setHeading(59);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(35.06310000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.39990000));
                    cache.put("b925aa4c-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA1121");
                    telemetry.setAltitude(19134);
                    telemetry.setAirspeed(237);
                    telemetry.setHeading(217);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(41.40140000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.47410000));
                    cache.put("b925aa6f-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8342");
                    telemetry.setAltitude(24475);
                    telemetry.setAirspeed(210);
                    telemetry.setHeading(263);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.08480000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.55900000));
                    cache.put("b925aa8c-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8356");
                    telemetry.setAltitude(10636);
                    telemetry.setAirspeed(246);
                    telemetry.setHeading(260);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(37.79330000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.32820000));
                    cache.put("b925aaa7-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9567");
                    telemetry.setAltitude(23305);
                    telemetry.setAirspeed(206);
                    telemetry.setHeading(164);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.30070000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.06190000));
                    cache.put("b925aac1-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4975");
                    telemetry.setAltitude(13538);
                    telemetry.setAirspeed(235);
                    telemetry.setHeading(186);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(43.58870000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.82120000));
                    cache.put("b925aada-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2137");
                    telemetry.setAltitude(21333);
                    telemetry.setAirspeed(189);
                    telemetry.setHeading(210);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(39.78680000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.97590000));
                    cache.put("b925aaf3-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8222");
                    telemetry.setAltitude(13996);
                    telemetry.setAirspeed(185);
                    telemetry.setHeading(244);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(46.01230000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.24270000));
                    cache.put("b925ab49-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3517");
                    telemetry.setAltitude(15220);
                    telemetry.setAirspeed(222);
                    telemetry.setHeading(12);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.72280000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.20660000));
                    cache.put("b925ab6a-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA1871");
                    telemetry.setAltitude(15346);
                    telemetry.setAirspeed(216);
                    telemetry.setHeading(57);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.04100000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.04580000));
                    cache.put("b925ab89-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0649");
                    telemetry.setAltitude(14516);
                    telemetry.setAirspeed(216);
                    telemetry.setHeading(213);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(42.38370000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.03620000));
                    cache.put("b925aba0-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6529");
                    telemetry.setAltitude(10579);
                    telemetry.setAirspeed(188);
                    telemetry.setHeading(333);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.95550000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.56250000));
                    cache.put("b925abb9-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4500");
                    telemetry.setAltitude(24456);
                    telemetry.setAirspeed(248);
                    telemetry.setHeading(310);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.25920000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.66130000));
                    cache.put("b925abd3-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0316");
                    telemetry.setAltitude(14385);
                    telemetry.setAirspeed(246);
                    telemetry.setHeading(319);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.55380000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.97200000));
                    cache.put("b925abf2-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7575");
                    telemetry.setAltitude(14858);
                    telemetry.setAirspeed(194);
                    telemetry.setHeading(166);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.81340000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.42280000));
                    cache.put("b925ac11-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2623");
                    telemetry.setAltitude(13499);
                    telemetry.setAirspeed(192);
                    telemetry.setHeading(170);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(36.63680000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.18420000));
                    cache.put("b925ac31-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7899");
                    telemetry.setAltitude(11832);
                    telemetry.setAirspeed(183);
                    telemetry.setHeading(202);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(33.69390000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.18430000));
                    cache.put("b925ac7a-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4305");
                    telemetry.setAltitude(16399);
                    telemetry.setAirspeed(203);
                    telemetry.setHeading(176);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(46.47560000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.00160000));
                    cache.put("b925ac96-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3132");
                    telemetry.setAltitude(12116);
                    telemetry.setAirspeed(183);
                    telemetry.setHeading(94);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(40.30960000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.47100000));
                    cache.put("b925acb6-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5501");
                    telemetry.setAltitude(20829);
                    telemetry.setAirspeed(195);
                    telemetry.setHeading(77);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.08420000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.62470000));
                    cache.put("b925acd2-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5757");
                    telemetry.setAltitude(17059);
                    telemetry.setAirspeed(250);
                    telemetry.setHeading(184);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.45890000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.98120000));
                    cache.put("b925acee-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5420");
                    telemetry.setAltitude(20030);
                    telemetry.setAirspeed(217);
                    telemetry.setHeading(103);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(40.91340000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.58980000));
                    cache.put("b925ad0b-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8654");
                    telemetry.setAltitude(13684);
                    telemetry.setAirspeed(241);
                    telemetry.setHeading(152);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(41.95180000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.02420000));
                    cache.put("b925ad23-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9006");
                    telemetry.setAltitude(10521);
                    telemetry.setAirspeed(249);
                    telemetry.setHeading(214);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(44.33400000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.80030000));
                    cache.put("b925ad3a-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3359");
                    telemetry.setAltitude(23683);
                    telemetry.setAirspeed(206);
                    telemetry.setHeading(279);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(47.82630000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.42990000));
                    cache.put("b925ad52-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5957");
                    telemetry.setAltitude(20679);
                    telemetry.setAirspeed(237);
                    telemetry.setHeading(232);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(46.91710000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.80240000));
                    cache.put("b925ada3-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6194");
                    telemetry.setAltitude(19059);
                    telemetry.setAirspeed(201);
                    telemetry.setHeading(340);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.80630000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.01350000));
                    cache.put("b925adc4-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0931");
                    telemetry.setAltitude(16103);
                    telemetry.setAirspeed(246);
                    telemetry.setHeading(51);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.98370000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.67850000));
                    cache.put("b925ade0-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2685");
                    telemetry.setAltitude(11952);
                    telemetry.setAirspeed(185);
                    telemetry.setHeading(160);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(43.29160000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.50780000));
                    cache.put("b925adf8-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5615");
                    telemetry.setAltitude(15864);
                    telemetry.setAirspeed(207);
                    telemetry.setHeading(120);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.67390000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.29440000));
                    cache.put("b925ae10-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6705");
                    telemetry.setAltitude(20962);
                    telemetry.setAirspeed(182);
                    telemetry.setHeading(350);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(33.53450000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.33350000));
                    cache.put("b925ae2b-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4947");
                    telemetry.setAltitude(24897);
                    telemetry.setAirspeed(197);
                    telemetry.setHeading(55);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(47.22820000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.76520000));
                    cache.put("b925ae45-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4277");
                    telemetry.setAltitude(24016);
                    telemetry.setAirspeed(220);
                    telemetry.setHeading(333);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.45900000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.45670000));
                    cache.put("b925ae63-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA1267");
                    telemetry.setAltitude(11978);
                    telemetry.setAirspeed(199);
                    telemetry.setHeading(188);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(42.10090000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.84000000));
                    cache.put("b925ae82-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9434");
                    telemetry.setAltitude(13162);
                    telemetry.setAirspeed(200);
                    telemetry.setHeading(296);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.05860000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.54500000));
                    cache.put("b925b42a-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3650");
                    telemetry.setAltitude(16659);
                    telemetry.setAirspeed(215);
                    telemetry.setHeading(144);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(41.46320000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.00600000));
                    cache.put("b925b44b-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0600");
                    telemetry.setAltitude(16483);
                    telemetry.setAirspeed(201);
                    telemetry.setHeading(271);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(32.10910000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.11050000));
                    cache.put("b925b469-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2282");
                    telemetry.setAltitude(10410);
                    telemetry.setAirspeed(233);
                    telemetry.setHeading(56);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.82530000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.29830000));
                    cache.put("b925b486-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0508");
                    telemetry.setAltitude(22677);
                    telemetry.setAirspeed(249);
                    telemetry.setHeading(47);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(33.23420000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.79530000));
                    cache.put("b925b4a3-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9649");
                    telemetry.setAltitude(18999);
                    telemetry.setAirspeed(217);
                    telemetry.setHeading(28);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(39.60500000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.16110000));
                    cache.put("b925b4be-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8267");
                    telemetry.setAltitude(13416);
                    telemetry.setAirspeed(233);
                    telemetry.setHeading(159);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(33.04010000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.69470000));
                    cache.put("b925b4d7-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2587");
                    telemetry.setAltitude(19117);
                    telemetry.setAirspeed(210);
                    telemetry.setHeading(149);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.77270000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.69510000));
                    cache.put("b925b4ef-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4399");
                    telemetry.setAltitude(10533);
                    telemetry.setAirspeed(249);
                    telemetry.setHeading(81);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(47.41040000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.11300000));
                    cache.put("b925b508-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2980");
                    telemetry.setAltitude(21576);
                    telemetry.setAirspeed(186);
                    telemetry.setHeading(68);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(41.13670000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.18490000));
                    cache.put("b925b558-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5639");
                    telemetry.setAltitude(21611);
                    telemetry.setAirspeed(186);
                    telemetry.setHeading(65);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.00810000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.29540000));
                    cache.put("b925b578-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9846");
                    telemetry.setAltitude(14422);
                    telemetry.setAirspeed(249);
                    telemetry.setHeading(178);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(35.08500000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.44250000));
                    cache.put("b925b593-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7353");
                    telemetry.setAltitude(10464);
                    telemetry.setAirspeed(215);
                    telemetry.setHeading(219);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(35.85510000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.76250000));
                    cache.put("b925b5ab-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6221");
                    telemetry.setAltitude(17951);
                    telemetry.setAirspeed(181);
                    telemetry.setHeading(89);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.28880000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.17810000));
                    cache.put("b925b5c3-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7744");
                    telemetry.setAltitude(16822);
                    telemetry.setAirspeed(187);
                    telemetry.setHeading(125);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.37190000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.95490000));
                    cache.put("b925b5da-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA1240");
                    telemetry.setAltitude(14780);
                    telemetry.setAirspeed(235);
                    telemetry.setHeading(28);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.90440000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.08190000));
                    cache.put("b925b5f7-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7731");
                    telemetry.setAltitude(19404);
                    telemetry.setAirspeed(222);
                    telemetry.setHeading(347);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.39600000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.23900000));
                    cache.put("b925b613-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2472");
                    telemetry.setAltitude(19543);
                    telemetry.setAirspeed(194);
                    telemetry.setHeading(41);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.16650000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.37530000));
                    cache.put("b925b630-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3899");
                    telemetry.setAltitude(14892);
                    telemetry.setAirspeed(248);
                    telemetry.setHeading(221);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(43.37690000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.70020000));
                    cache.put("b925b679-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3748");
                    telemetry.setAltitude(10092);
                    telemetry.setAirspeed(192);
                    telemetry.setHeading(85);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.45280000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.92080000));
                    cache.put("b925b694-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7956");
                    telemetry.setAltitude(20882);
                    telemetry.setAirspeed(214);
                    telemetry.setHeading(2);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(39.59550000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.05930000));
                    cache.put("b925b6af-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3777");
                    telemetry.setAltitude(13289);
                    telemetry.setAirspeed(200);
                    telemetry.setHeading(140);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(42.89460000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.88760000));
                    cache.put("b925b6cc-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7204");
                    telemetry.setAltitude(10367);
                    telemetry.setAirspeed(218);
                    telemetry.setHeading(125);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(32.05610000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.41730000));
                    cache.put("b925b6e7-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5015");
                    telemetry.setAltitude(13743);
                    telemetry.setAirspeed(217);
                    telemetry.setHeading(63);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(47.38900000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.63030000));
                    cache.put("b925b704-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4230");
                    telemetry.setAltitude(13348);
                    telemetry.setAirspeed(238);
                    telemetry.setHeading(64);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.25230000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.33980000));
                    cache.put("b925b71d-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9599");
                    telemetry.setAltitude(16383);
                    telemetry.setAirspeed(181);
                    telemetry.setHeading(256);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(36.73950000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.28610000));
                    cache.put("b925b733-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5316");
                    telemetry.setAltitude(23696);
                    telemetry.setAirspeed(215);
                    telemetry.setHeading(345);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(36.60630000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.00090000));
                    cache.put("b925b74b-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7475");
                    telemetry.setAltitude(13271);
                    telemetry.setAirspeed(242);
                    telemetry.setHeading(308);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(32.93280000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.89920000));
                    cache.put("b925b795-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3819");
                    telemetry.setAltitude(18099);
                    telemetry.setAirspeed(209);
                    telemetry.setHeading(338);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(34.23250000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.40450000));
                    cache.put("b925b7b8-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4634");
                    telemetry.setAltitude(14108);
                    telemetry.setAirspeed(221);
                    telemetry.setHeading(222);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(41.65980000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.31470000));
                    cache.put("b925b7d5-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7827");
                    telemetry.setAltitude(12284);
                    telemetry.setAirspeed(240);
                    telemetry.setHeading(278);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(35.58570000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.89280000));
                    cache.put("b925b88b-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2711");
                    telemetry.setAltitude(17681);
                    telemetry.setAirspeed(220);
                    telemetry.setHeading(5);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(32.00740000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.28730000));
                    cache.put("b925b8c5-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6705");
                    telemetry.setAltitude(24691);
                    telemetry.setAirspeed(201);
                    telemetry.setHeading(90);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.99050000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.44960000));
                    cache.put("b925b8df-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA4074");
                    telemetry.setAltitude(14319);
                    telemetry.setAirspeed(201);
                    telemetry.setHeading(132);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(46.98590000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.58750000));
                    cache.put("b925b8f7-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3794");
                    telemetry.setAltitude(10173);
                    telemetry.setAirspeed(224);
                    telemetry.setHeading(127);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(36.79260000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.70320000));
                    cache.put("b925b90d-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9097");
                    telemetry.setAltitude(23039);
                    telemetry.setAirspeed(248);
                    telemetry.setHeading(144);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(33.54200000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.48150000));
                    cache.put("b925b925-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9338");
                    telemetry.setAltitude(16207);
                    telemetry.setAirspeed(197);
                    telemetry.setHeading(193);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(32.96790000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.41660000));
                    cache.put("b925b97f-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5878");
                    telemetry.setAltitude(21564);
                    telemetry.setAirspeed(184);
                    telemetry.setHeading(125);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.02150000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.35720000));
                    cache.put("b925b99c-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8452");
                    telemetry.setAltitude(18189);
                    telemetry.setAirspeed(239);
                    telemetry.setHeading(129);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(35.28030000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.41160000));
                    cache.put("b925b9b4-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7591");
                    telemetry.setAltitude(24746);
                    telemetry.setAirspeed(228);
                    telemetry.setHeading(140);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.86100000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.85030000));
                    cache.put("b925bb1e-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9097");
                    telemetry.setAltitude(11329);
                    telemetry.setAirspeed(215);
                    telemetry.setHeading(63);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(34.54500000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.80450000));
                    cache.put("b925bb3f-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2635");
                    telemetry.setAltitude(20991);
                    telemetry.setAirspeed(199);
                    telemetry.setHeading(190);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(40.55720000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.19370000));
                    cache.put("b925bb57-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6511");
                    telemetry.setAltitude(10738);
                    telemetry.setAirspeed(196);
                    telemetry.setHeading(185);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.92200000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-83.36700000));
                    cache.put("b925bb6e-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7858");
                    telemetry.setAltitude(17160);
                    telemetry.setAirspeed(193);
                    telemetry.setHeading(142);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(42.19540000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.26720000));
                    cache.put("b925bb84-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7476");
                    telemetry.setAltitude(18238);
                    telemetry.setAirspeed(226);
                    telemetry.setHeading(253);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(40.66420000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.86630000));
                    cache.put("b925bb9a-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2302");
                    telemetry.setAltitude(23752);
                    telemetry.setAirspeed(241);
                    telemetry.setHeading(41);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(34.56040000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.60820000));
                    cache.put("b925bbe7-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA8645");
                    telemetry.setAltitude(23886);
                    telemetry.setAirspeed(235);
                    telemetry.setHeading(318);
                    telemetry.setCurrentSegment(1);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(34.71490000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.99530000));
                    cache.put("b925bc08-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3157");
                    telemetry.setAltitude(13193);
                    telemetry.setAirspeed(244);
                    telemetry.setHeading(136);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(40.85700000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.98800000));
                    cache.put("b925bc26-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9784");
                    telemetry.setAltitude(20916);
                    telemetry.setAirspeed(247);
                    telemetry.setHeading(38);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(35.31660000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-82.16480000));
                    cache.put("b925bc43-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA2525");
                    telemetry.setAltitude(16562);
                    telemetry.setAirspeed(247);
                    telemetry.setHeading(259);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(41.93290000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.37000000));
                    cache.put("b925bc5c-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA6560");
                    telemetry.setAltitude(13344);
                    telemetry.setAirspeed(185);
                    telemetry.setHeading(136);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(38.78630000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.83700000));
                    cache.put("b925bc74-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3515");
                    telemetry.setAltitude(19417);
                    telemetry.setAirspeed(213);
                    telemetry.setHeading(41);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(45.85350000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.84840000));
                    cache.put("b925bc8b-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7085");
                    telemetry.setAltitude(23840);
                    telemetry.setAirspeed(246);
                    telemetry.setHeading(41);
                    telemetry.setCurrentSegment(3);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(43.91500000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-84.76010000));
                    cache.put("b925bca4-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA9796");
                    telemetry.setAltitude(15688);
                    telemetry.setAirspeed(250);
                    telemetry.setHeading(57);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(30.61400000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.48860000));
                    cache.put("b925bcc0-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3609");
                    telemetry.setAltitude(12718);
                    telemetry.setAirspeed(228);
                    telemetry.setHeading(83);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(32.95390000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.93780000));
                    cache.put("b925bd0d-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA0099");
                    telemetry.setAltitude(24302);
                    telemetry.setAirspeed(232);
                    telemetry.setHeading(314);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(47.51110000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-81.16010000));
                    cache.put("b925bd29-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA3358");
                    telemetry.setAltitude(15639);
                    telemetry.setAirspeed(240);
                    telemetry.setHeading(131);
                    telemetry.setCurrentSegment(5);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(37.85390000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.76280000));
                    cache.put("b925bd42-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA7189");
                    telemetry.setAltitude(21648);
                    telemetry.setAirspeed(191);
                    telemetry.setHeading(165);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(40.31580000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-86.29880000));
                    cache.put("b925bd59-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5740");
                    telemetry.setAltitude(12479);
                    telemetry.setAirspeed(184);
                    telemetry.setHeading(110);
                    telemetry.setCurrentSegment(4);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(42.54110000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-85.83310000));
                    cache.put("b925bd70-cc34-11e8-849f-000c2995cc51", telemetry);

                    telemetry = new Telemetry();
                    telemetry.setRouteId("kcle_kbos");
                    telemetry.setFlightNumber("DA5312");
                    telemetry.setAltitude(21973);
                    telemetry.setAirspeed(215);
                    telemetry.setHeading(77);
                    telemetry.setCurrentSegment(2);
                    telemetry.setPositionLatitude(BigDecimal.valueOf(31.22460000));
                    telemetry.setPositionLongitude(BigDecimal.valueOf(-80.06860000));
                    cache.put("b925bd8c-cc34-11e8-849f-000c2995cc51", telemetry);

/*************************************************************************************************************
 *              End of simulated data that is being added to the cache                                       *
 *************************************************************************************************************/

                }
            } finally {
                ignite.destroyCache("TelemetryCache");
            }
        }
    }
}
