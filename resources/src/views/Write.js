import React from 'react';
<<<<<<< HEAD
import { Container, Grid, Box, Typography, Divider, FormControl, OutlinedInput, Button } from '@mui/material';
import { EditorState, RichUtils } from "draft-js";
import RichEditor from '../components/RichEditor';
=======
import { Container, Grid, Box, Typography, Divider, FormControl, OutlinedInput, ButtonGroup, IconButton } from '@mui/material';
import { Editor, EditorState} from "draft-js";
import { styled } from '@mui/material/styles';

const EditorControl = styled(FormControl)(({ theme }) => ({
	border: 'solid grey',
	borderWidth: 'thin',
  borderRadius: theme.shape.borderRadius,
	'&:hover': {
		borderColor: theme.palette.text.primary
	},
}));
>>>>>>> 7acd8b29d13b06ed046e61ec7974be0eec87d186

class Write extends React.Component {
	constructor(props) {
		super(props);
		this.state = { editorState: EditorState.createEmpty() };
	}

<<<<<<< HEAD
	onClickUndo = () => {
		this.onEditorStateChange(EditorState.undo(this.state.editorState));
	}

	onClickRedo = () => {
		this.onEditorStateChange(EditorState.redo(this.state.editorState));
	}

	onToggleInlineStyle = (style) => (event) => {
		event.preventDefault();
		this.onEditorStateChange(RichUtils.toggleInlineStyle(this.state.editorState, style));
	}

	onEditorChange = (editorState) => {
=======
	setEditor = (editor) => {
		this.editor = editor;
	}

	onEditorStateChange = (editorState) => {
>>>>>>> 7acd8b29d13b06ed046e61ec7974be0eec87d186
		this.setState({ editorState });
	}

	onFocusEditor = () => {
		if (this.editor) {
			this.editor.focus();
		}
	}

	render() {
		return(
			<Container maxWidth={false}>
<<<<<<< HEAD
				<Grid container alignContent="center" style={{ minHeight: "100vh", maxHeight: "100vh" }}>
					<Box textAlign="center" mx="auto"  width="1024px" maxWidth="1024px">
						<Typography align="left" gutterBottom  variant="h4">게시물 작성</Typography>
						<Divider />
						<Box component="form">
							<FormControl margin="normal" sx={{ width: '100%' }}>
								<OutlinedInput placeholder="제목" />
							</FormControl>
							<FormControl margin="normal" sx={{ width: '100%' }}>
								<RichEditor editorState={this.state.editorState} onChange={this.onEditorChange.bind(this)} />
							</FormControl>
							<Button fullWidth variant="contained" sx={{ mt: 1, mb: 2 }}>작성</Button>
=======
				<Grid container alignContent="center" style={{ minHeight: "100vh" }}>
					<Box textAlign="center" mx="auto">
						<Typography align="left" gutterBottom  variant="h4">게시물 작성</Typography>
						<Divider />
						<Box component="form" width="512dp">
							<FormControl margin="normal" sx={{ width: '100ch' }} onClick={this.forceUpdate}>
								<OutlinedInput placeholder="제목" />
							</FormControl>
							<EditorControl margin="normal" sx={{ width: '100ch' }}>
								<Editor ref={this.setEditor} editorState={this.state.editorState} onChange={this.onEditorStateChange} />
							</EditorControl>
>>>>>>> 7acd8b29d13b06ed046e61ec7974be0eec87d186
						</Box>
					</Box>
				</Grid>
			</Container>
		)
	}
}

export default Write;