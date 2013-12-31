--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.2
-- Dumped by pg_dump version 9.2.2
-- Started on 2013-12-31 15:19:11 GMT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 173 (class 3079 OID 11995)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 173
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 169 (class 1259 OID 272250)
-- Name: profile; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE profile (
    id integer NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    email character varying(255),
    "profileImage" character varying(1024)
);


ALTER TABLE public.profile OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 272248)
-- Name: Profile_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Profile_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Profile_id_seq" OWNER TO postgres;

--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 168
-- Name: Profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Profile_id_seq" OWNED BY profile.id;


--
-- TOC entry 171 (class 1259 OID 279206)
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comment (
    id integer NOT NULL,
    "userId" integer,
    comment character varying(2048),
    created timestamp without time zone
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 279204)
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_id_seq OWNER TO postgres;

--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 170
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE comments_id_seq OWNED BY comment.id;


--
-- TOC entry 172 (class 1259 OID 279220)
-- Name: connection; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE connection (
    "userId" integer NOT NULL,
    following integer NOT NULL
);


ALTER TABLE public.connection OWNER TO postgres;

--
-- TOC entry 2195 (class 2604 OID 279209)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comment ALTER COLUMN id SET DEFAULT nextval('comments_id_seq'::regclass);


--
-- TOC entry 2194 (class 2604 OID 272253)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY profile ALTER COLUMN id SET DEFAULT nextval('"Profile_id_seq"'::regclass);


--
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 168
-- Name: Profile_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"Profile_id_seq"', 1, true);


--
-- TOC entry 2208 (class 0 OID 279206)
-- Dependencies: 171
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comment (id, "userId", comment, created) FROM stdin;
\.


--
-- TOC entry 2221 (class 0 OID 0)
-- Dependencies: 170
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comments_id_seq', 1, false);


--
-- TOC entry 2209 (class 0 OID 279220)
-- Dependencies: 172
-- Data for Name: connection; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY connection ("userId", following) FROM stdin;
\.


--
-- TOC entry 2206 (class 0 OID 272250)
-- Dependencies: 169
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY profile (id, firstname, lastname, email, "profileImage") FROM stdin;
1	Mega	Pode	mega.pode@bigfootbirdie.com	\N
\.


--
-- TOC entry 2197 (class 2606 OID 272258)
-- Name: Profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT "Profile_pkey" PRIMARY KEY (id);


--
-- TOC entry 2199 (class 2606 OID 279214)
-- Name: comment_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_id PRIMARY KEY (id);


--
-- TOC entry 2201 (class 2606 OID 279224)
-- Name: connection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY connection
    ADD CONSTRAINT connection_pkey PRIMARY KEY ("userId", following);


--
-- TOC entry 2202 (class 2606 OID 279235)
-- Name: comments_userId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT "comments_userId_fkey" FOREIGN KEY ("userId") REFERENCES profile(id);


--
-- TOC entry 2204 (class 2606 OID 279230)
-- Name: connection_following_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY connection
    ADD CONSTRAINT connection_following_fkey FOREIGN KEY (following) REFERENCES profile(id);


--
-- TOC entry 2203 (class 2606 OID 279225)
-- Name: connection_userId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY connection
    ADD CONSTRAINT "connection_userId_fkey" FOREIGN KEY ("userId") REFERENCES profile(id);


--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-12-31 15:19:11 GMT

--
-- PostgreSQL database dump complete
--

