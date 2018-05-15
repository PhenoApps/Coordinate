CREATE TABLE templates
(
    _id     INTEGER PRIMARY KEY AUTOINCREMENT,
    title   TEXT    NOT NULL UNIQUE,
    type    INTEGER NOT NULL CHECK(type BETWEEN 0 AND 2),
    rows    INTEGER NOT NULL CHECK(rows >= 0),
    cols    INTEGER NOT NULL CHECK(cols >= 0),

    erand   INTEGER CHECK(erand >= 0),
    ecells  TEXT,
    erows   TEXT,
    ecols   TEXT,

    cnumb   INTEGER NOT NULL CHECK(cnumb BETWEEN 0 AND 1),
    rnumb   INTEGER NOT NULL CHECK(rnumb BETWEEN 0 AND 1),

    options TEXT,

    stamp   INTEGER
)