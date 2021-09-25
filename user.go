package algoweb

import (
	"bytes"
	"crypto/rsa"
	"encoding/json"
	"encoding/xml"
	"errors"
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
	"time"

	"github.com/gofiber/fiber/v2"
	"github.com/golang-jwt/jwt/v4"
	"gorm.io/gorm"
)

type User struct {
	gorm.Model
	StudentId   string    `json:"studentId" gorm:"unique"`
	Name        string    `json:"name"`
	Birth       time.Time `json:"birth"`
	PhoneNumber string    `json:"phoneNumber"`
	Admin       int       `json:"admin"`
	LastLogin   time.Time `json:"lastLogin"`
}

type UserController struct {
	db         *gorm.DB
	privateKey *rsa.PrivateKey
}

type Root struct {
	XMLName    xml.Name   `xml:"Root"`
	XMLns      string     `xml:"xmlns,attr"`
	Parameters Parameters `xml:"Parameters"`
	Dataset    []Dataset  `xml:"Dataset"`
}

type Parameters struct {
	Parameters []Parameter `xml:"Parameter"`
}

type Parameter struct {
	Id    string `xml:"id,attr"`
	Value string `xml:",chardata"`
}

type Dataset struct {
	Id         string     `xml:"id,attr"`
	ColumnInfo ColumnInfo `xml:"ColumnInfo"`
	Rows       Rows       `xml:"Rows"`
}

type ColumnInfo struct {
	Columns []Column `xml:"Column"`
}

type Column struct {
	Id   string `xml:"id,attr"`
	Type string `xml:"type,attr"`
	Size string `xml:"size,attr"`
}

type Rows struct {
	Rows []Row `xml:"Row"`
}

type Row struct {
	Cols []Col `xml:"Col"`
}

type Col struct {
	Id    string `xml:"id,attr"`
	Value string `xml:",chardata"`
}

func (u *UserController) auth(c *fiber.Ctx) error {
	response := make(map[string]interface{})
	var body map[string]interface{}
	err := json.Unmarshal([]byte(c.Body()), &body)
	if err != nil {
		log.Println(err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}

	var root Root
	root.XMLns = "http://www.nexacroplatform.com/platform/dataset"
	var parms Parameters
	parms.Parameters = append(parms.Parameters, Parameter{"fsp_action", "JJLogin"})
	parms.Parameters = append(parms.Parameters, Parameter{"fsp_cmd", "login"})
	parms.Parameters = append(parms.Parameters, Parameter{"GV_USER_ID", "undefined"})
	parms.Parameters = append(parms.Parameters, Parameter{"GV_IP_ADDRESS", "undefined"})
	parms.Parameters = append(parms.Parameters, Parameter{"fsp_logId", "all"})
	parms.Parameters = append(parms.Parameters, Parameter{"MAX_WRONG_COUNT", "5"})
	root.Parameters = parms

	var dataset Dataset
	dataset.Id = "ds_cond"
	var columnInfo ColumnInfo
	columnInfo.Columns = make([]Column, 0)
	columnInfo.Columns = append(columnInfo.Columns, Column{"SYSTEM_CODE", "STRING", "256"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"MEM_ID", "STRING", "10"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"MEM_PW", "STRING", "15"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"MEM_IP", "STRING", "20"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"ROLE_GUBUN1", "STRING", "256"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"ROLE_GUBUN2", "STRING", "256"})
	dataset.ColumnInfo = columnInfo
	var rows Rows
	var row Row
	row.Cols = make([]Col, 0)
	row.Cols = append(row.Cols, Col{"SYSTEM_CODE", "INSTAR_WEB"})
	row.Cols = append(row.Cols, Col{"MEM_ID", body["studentId"].(string)})
	row.Cols = append(row.Cols, Col{"MEM_PW", body["password"].(string)})
	rows.Rows = make([]Row, 0)
	rows.Rows = append(rows.Rows, row)
	dataset.Rows = rows
	root.Dataset = append(root.Dataset, dataset)

	dataset.Id = "fsp_sd_cmd"
	columnInfo.Columns = make([]Column, 0)
	columnInfo.Columns = append(columnInfo.Columns, Column{"TX_NAME", "STRING", "100"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"TYPE", "STRING", "10"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"SQL_ID", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"KEY_SQL_ID", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"KEY_INCREMENT", "INT", "10"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"CALLBACK_SQL_ID", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"INSERT_SQL_ID", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"UPDATE_SQL_ID", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"DELETE_SQL_ID", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"SAVE_FLAG_COLUMN", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"USE_INPUT", "STRING", "1"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"USE_ORDER", "STRING", "1"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"KEY_ZERO_LEN", "INT", "10"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"BIZ_NAME", "STRING", "100"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"PAGE_NO", "INT", "10"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"PAGE_SIZE", "INT", "10"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"READ_ALL", "STRING", "1"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"EXEC_TYPE", "STRING", "2"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"EXEC", "STRING", "1"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"FAIL", "STRING", "1"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"FAIL_MSG", "STRING", "200"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"EXEC_CNT", "INT", "1"})
	columnInfo.Columns = append(columnInfo.Columns, Column{"MSG", "STRING", "200"})
	dataset.ColumnInfo = columnInfo
	row.Cols = make([]Col, 0)
	rows.Rows = make([]Row, 0)
	rows.Rows = append(rows.Rows, row)
	dataset.Rows = rows
	root.Dataset = append(root.Dataset, dataset)

	dataset.Id = "gds_user"
	columnInfo.Columns = nil
	dataset.ColumnInfo = columnInfo
	row.Cols = nil
	rows.Rows = nil
	dataset.Rows = rows
	root.Dataset = append(root.Dataset, dataset)

	temp, err := xml.Marshal(root)
	if err != nil {
		log.Println(err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}
	pbytes := []byte(xml.Header)
	pbytes = append(pbytes, temp...)
	buff := bytes.NewBuffer(pbytes)

	resp, err := http.Post("https://instar.jj.ac.kr/XMain", "application/xml", buff)
	if err != nil {
		log.Println(err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}
	defer resp.Body.Close()

	respBody, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Println(err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}
	var result Root
	xml.Unmarshal(respBody, &result)
	index := len(result.Dataset)
	for i, d := range result.Dataset {
		if d.Id == "ds_info" {
			index = i
			break
		}
	}
	if index == len(result.Dataset) {
		response["result"] = false
		response["message"] = "학번 또는 비밀번호가 잘못되었습니다."
		return c.Status(fiber.StatusUnauthorized).JSON(response)
	}

	response["result"] = true
	if !body["login"].(bool) {
		m := make(map[string]interface{})
		columns := result.Dataset[index].Rows.Rows[0].Cols
		for _, c := range columns {
			m[c.Id] = c.Value
		}
		response["member"] = m
		return c.JSON(response)
	}

	var user User
	r := u.db.First(&user, "student_id = ?", body["studentId"])
	if errors.Is(r.Error, gorm.ErrRecordNotFound) {
		response["message"] = "등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요."
		return c.Status(fiber.StatusUnauthorized).JSON(response)
	}

	token := jwt.New(jwt.SigningMethodRS256)

	claims := token.Claims.(jwt.MapClaims)
	claims["userid"] = user.ID
	claims["exp"] = time.Now().Add(time.Minute * 15).Unix()

	t, err := token.SignedString(u.privateKey)
	if err != nil {
		log.Printf("token.SignedString: %v", err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}

	response["token"] = t

	return c.JSON(response)
}

func authJJ(id string, pw string) bool {
	resp, err := http.PostForm(
		"https://www.jj.ac.kr/_custom/jj/_common/new_login/proc/login_proc_new.jsp",
		url.Values{
			"pre_page":  {"https://www.jj.ac.kr/jj/index.jsp"},
			"member_id": {id},
			"member_pw": {pw}})

	if err != nil {
		log.Fatalln(err)
	}
	defer resp.Body.Close()

	for _, cookie := range resp.Cookies() {
		if cookie.Name == "jj_visited" && cookie.Value == "Y" {
			return true
		}
	}

	return false
}

func (u *UserController) me(c *fiber.Ctx) error {
	token := c.Locals("user").(*jwt.Token)
	claims := token.Claims.(jwt.MapClaims)
	userId := uint(claims["userid"].(float64))
	var user User
	u.db.First(&user, userId)
	return c.JSON(user)
}

func (u *UserController) login(c *fiber.Ctx) error {
	var result fiber.Map
	result = make(map[string]interface{})

	var body map[string]interface{}
	err := json.Unmarshal([]byte(c.Body()), &body)
	if err != nil {
		log.Println(err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}

	success := authJJ(body["studentId"].(string), body["password"].(string))
	result["result"] = success
	if !success {
		result["message"] = "학번 또는 비밀번호가 잘못되었습니다."
		return c.Status(fiber.StatusUnauthorized).JSON(result)
	}

	var user User
	r := u.db.First(&user, "student_id = ?", body["studentId"])
	if errors.Is(r.Error, gorm.ErrRecordNotFound) {
		result["message"] = "등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요."
		return c.Status(fiber.StatusUnauthorized).JSON(result)
	}

	token := jwt.New(jwt.SigningMethodRS256)

	claims := token.Claims.(jwt.MapClaims)
	claims["userid"] = user.ID
	claims["exp"] = time.Now().Add(time.Minute * 15).Unix()

	t, err := token.SignedString(u.privateKey)
	if err != nil {
		log.Printf("token.SignedString: %v", err)
		return c.SendStatus(fiber.StatusInternalServerError)
	}

	result["token"] = t

	return c.JSON(result)
}
