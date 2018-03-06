CREATE TABLE templates
(
    _id     INTEGER PRIMARY KEY AUTOINCREMENT,
    title   TEXT,
    type    INTEGER CHECK(type BETWEEN 0 AND 2),
    rows    INTEGER CHECK(rows >= 0),
    cols    INTEGER CHECK(cols >= 0),

    erand   INTEGER CHECK(erand >= 0),
    ecells  TEXT,
    erows   TEXT,
    ecols   TEXT,

    cnumb   INTEGER CHECK(cnumb BETWEEN 0 AND 1),
    rnumb   INTEGER CHECK(rnumb BETWEEN 0 AND 1),

    options TEXT,

    stamp   INTEGER
)