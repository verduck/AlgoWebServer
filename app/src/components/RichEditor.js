import React from "react";
import { Editor, EditorState, RichUtils } from "draft-js";
import { Paper, Grid, Tooltip, IconButton,  Divider } from '@mui/material';
import { styled } from '@mui/material/styles';
import UndoIcon from '@mui/icons-material/Undo';
import RedoIcon from '@mui/icons-material/Redo';
import FormatBoldIcon from '@mui/icons-material/FormatBold';
import FormatItalicIcon from '@mui/icons-material/FormatItalic';
import FormatUnderlinedIcon from '@mui/icons-material/FormatUnderlined';

const RichEditorRoot = styled('div')(({ theme }) => ({
	
}));

const RichEditorControls = styled(Paper)(({ theme }) => ({
	marginBottom: 10,
}));

const RichEditorEditor = styled('div')(({ theme }) => ({
	position: 'relative',
	padding: '14px',
	border: '1px solid #ccc',
	borderRadius: 4,
	cursor: 'text',
	height: 512,
	'&:hover': {
		borderColor: theme.palette.text.primary
	},
	overflow: 'auto'
}));

export default class RichEditor extends React.Component {
  constructor(props) {
    super(props);
    this.onChange = this.props.onChange.bind(this);
  }

  onClickUndo = () => {
		this.onChange(EditorState.undo(this.props.editorState));
	}

	onClickRedo = () => {
		this.onChange(EditorState.redo(this.props.editorState));
	}

	onToggleInlineStyle = (style) => (event) => {
		event.preventDefault();
		this.onChange(RichUtils.toggleInlineStyle(this.props.editorState, style));
	}

	hasInlineStyle = (style) => {
		return this.props.editorState.getCurrentInlineStyle().has(style);
	}

  setEditor = (editor) => {
		this.editor = editor;
	}

  onFocusEditor = () => {
		if (this.editor) {
			this.editor.focus();
		}
	}

  render() {
    return (
      <RichEditorRoot className="RichEditor-root">
        <RichEditorControls className="RichEditor-controls" elevation={2}>
          <Grid container>
            <Tooltip title="실행 취소"><IconButton onClick={this.onClickUndo.bind(this)}><UndoIcon /></IconButton></Tooltip>
            <Tooltip title="다시 실행"><IconButton onClick={this.onClickRedo.bind(this)}><RedoIcon /></IconButton></Tooltip>
            <Divider orientation="vertical" flexItem />
            <Tooltip title="굵게"><IconButton color={this.hasInlineStyle('BOLD') ? "primary" : "default"} onMouseDown={this.onToggleInlineStyle('BOLD')}><FormatBoldIcon /></IconButton></Tooltip>
            <Tooltip title="기움일꼴"><IconButton color={this.hasInlineStyle('ITALIC') ? "primary" : "default"} onMouseDown={this.onToggleInlineStyle('ITALIC')}><FormatItalicIcon /></IconButton></Tooltip>
            <Tooltip title="밑줄"><IconButton color={this.hasInlineStyle('UNDERLINE') ? "primary" : "default"} onMouseDown={this.onToggleInlineStyle('UNDERLINE')}><FormatUnderlinedIcon /></IconButton></Tooltip>
            <Divider orientation="vertical" flexItem />
          </Grid>
        </RichEditorControls>
        <RichEditorEditor className="RichEditor-editor" align="left" onClick={this.onFocusEditor}>
          <Editor ref={this.setEditor} editorState={this.props.editorState} onChange={this.props.onChange} placeholder="내용을 입력하세요..." />
        </RichEditorEditor>
      </RichEditorRoot>
    )
  }
}