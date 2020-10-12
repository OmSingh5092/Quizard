const express = require('express');
const router = express.Router();

const facultyCtrl = require('../controllers/facultyCtrl')
const verifyUser = require('../middlewares/verifyMW').user;

router.get('/get',verifyUser,facultyCtrl.getProfile);
router.post('/update',verifyUser,facultyCtrl.updateProfile);

module.exports = router;